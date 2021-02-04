package com.appleyk.redis.service.impl;

import com.appleyk.redis.entity.CommodityStockEntity;
import com.appleyk.redis.repo.CommodityStockRepo;
import com.appleyk.redis.service.CommodityStockService;
import com.appleyk.redis.utils.RedisDistributeLock;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityNotFoundException;

/**
 * <p>商品库存业务接口具体实现类</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 14:58 2021/1/29
 */
@Service
public class CommodityStockServiceImpl implements CommodityStockService {

    private Logger logger = LoggerFactory.getLogger(CommodityStockServiceImpl.class);

    @Autowired
    private CommodityStockRepo stockRepo;

    @Autowired
    private RedisDistributeLock distributeLock;

    @Autowired
    private JpaTransactionManager transactionManager;

    /**
     * 第一种解决方案：service方法上不加事务注解，在具体的reduce（repo）方法上加事务注解
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer reduceLock0(String commodityCode) {
        RLock lock4Reduce = distributeLock.lock4Reduce(commodityCode);
        try{
            CommodityStockEntity stockEntity = stockRepo.findByCommodityCode(commodityCode);
            if(stockEntity == null){
                throw new EntityNotFoundException("指定的商品["+commodityCode+"]不存在！");
            }
            int inventory = stockEntity.getInventory();
            if(inventory == 0) {
                logger.warn("商品不够了，需要加库存！");
                return 0;
            }
            return stockRepo.reduce(commodityCode);
        }finally {
            if (lock4Reduce.isLocked()) {
                if (lock4Reduce.isHeldByCurrentThread()) {
                    lock4Reduce.unlock();
                }
            }
        }
    }

    /**
     * 第一种解决方案：service方法上不加事务注解，在具体的reduce（repo）方法上加事务注解
     */
    @Override
    public Integer reduceLock1(String commodityCode) {
        RLock lock4Reduce = distributeLock.lock4Reduce(commodityCode);
        try{
            CommodityStockEntity stockEntity = stockRepo.findByCommodityCode(commodityCode);
            if(stockEntity == null){
                throw new EntityNotFoundException("指定的商品["+commodityCode+"]不存在！");
            }
            int inventory = stockEntity.getInventory();
            if(inventory == 0) {
                logger.warn("商品不够了，需要加库存！");
                return 0;
            }
            return stockRepo.reduce(commodityCode);

        }finally {
            if (lock4Reduce.isLocked()) {
                if (lock4Reduce.isHeldByCurrentThread()) {
                    lock4Reduce.unlock();
                }
            }
        }
    }

    /**
     * 第二种解决方案： 使用事务同步管理器，约束释放锁的时机必须在事务完成后才可以
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer reduceLock2(String commodityCode) {
        RLock lock4Reduce = distributeLock.lock4Reduce(commodityCode);
        try{
            CommodityStockEntity stockEntity = stockRepo.findByCommodityCode(commodityCode);
            if(stockEntity == null){
                throw new EntityNotFoundException("指定的商品["+commodityCode+"]不存在！");
            }
            int inventory = stockEntity.getInventory();
            if(inventory == 0) {
                logger.warn("商品不够了，需要加库存！");
                return 0;
            }
            return stockRepo.reduce2(commodityCode);

        }finally {
            //事务完成后释放锁
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCompletion(int status) {
                    if (lock4Reduce.isLocked()) {
                        if (lock4Reduce.isHeldByCurrentThread()) {
                            lock4Reduce.unlock();
                        }
                    }
                }
            });
        }
    }

    /**
     * 第三种解决方案：service方法上不加@Transaction，在程序中，手动管理事务的提交和回滚
     * @param commodityCode 商品编码
     */
    @Override
    public Integer reduceLock3(String commodityCode) {
        int res = 0;
        RLock lock4Reduce = distributeLock.lock4Reduce(commodityCode);
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);
        try{
            CommodityStockEntity stockEntity = stockRepo.findByCommodityCode(commodityCode);
            if(stockEntity == null){
                throw new EntityNotFoundException("指定的商品["+commodityCode+"]不存在！");
            }
            int inventory = stockEntity.getInventory();
            if(inventory == 0) {
                logger.warn("商品不够了，需要加库存！");
                return  0;
            }
            stockEntity.setInventory(inventory-1);
            stockRepo.save(stockEntity);
            transactionManager.commit(txStatus);
            return 1;
        }catch (Exception e){
            transactionManager.rollback(txStatus);
        }finally {
            if (lock4Reduce.isLocked() && txStatus.isCompleted()) {
                if (lock4Reduce.isHeldByCurrentThread()) {
                    lock4Reduce.unlock();
                }
            }
        }
        return res;
    }

    /**
     * 第四种解决方案：压根就不加锁，由上层进行锁控制
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Integer reduceLock4(String commodityCode) {
        CommodityStockEntity stockEntity = stockRepo.findByCommodityCode(commodityCode);
        if(stockEntity == null){
            throw new EntityNotFoundException("指定的商品["+commodityCode+"]不存在！");
        }
        int inventory = stockEntity.getInventory();
        if(inventory == 0) {
            logger.warn("商品不够了，需要加库存！");
            return 0;
        }
        return stockRepo.reduce(commodityCode);
    }
}