package com.appleyk.redis.service.impl;

import com.appleyk.redis.entity.CommodityStockEntity;
import com.appleyk.redis.repo.CommodityStockRepo;
import com.appleyk.redis.service.CommodityStockService;
import com.appleyk.redis.utils.RedisDistributeLock;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 由于事务的隔离性（别人修改的数据，其他事务无法读取到），该方法会造成最终的库存量为负数，即超卖
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer reduce(String commodityCode) {
        CommodityStockEntity stockEntity = stockRepo.findByCommodityCode(commodityCode);
        if(stockEntity == null){
            throw new EntityNotFoundException("指定的商品["+commodityCode+"]不存在！");
        }
        int inventory = stockEntity.getInventory();

        if(inventory == 0){
            logger.warn("商品不够了，需要加库存！");
            return 0;
        }
        return stockRepo.reduce(commodityCode);
    }

    /**
     * 使用save，会造成库存"变多"，因为save会覆盖掉其他线程修改的值
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer reduce2(String commodityCode) {
        CommodityStockEntity stockEntity = stockRepo.findByCommodityCode(commodityCode);
        if(stockEntity == null){
            throw new EntityNotFoundException("指定的商品["+commodityCode+"]不存在！");
        }
        int inventory = stockEntity.getInventory();
        if(inventory == 0){
            logger.warn("商品不够了，需要加库存！");
            return 0;
        }
        stockEntity.setInventory(stockEntity.getInventory()-1);
        CommodityStockEntity save = stockRepo.save(stockEntity);
        logger.info("商品剩余库存<{}>",save.getInventory());
        return 1;
    }

    /**
     * 使用redssion框架提供的分布式锁，可以很容易的控制多线程减库存时，不会出现超卖的现象
     * @param commodityCode 商品编码
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer reduceLock(String commodityCode) {
        // 先获取锁,获取不到会阻塞
        RLock lock4Reduce = distributeLock.lock4Reduce(commodityCode);
        try{
            logger.info("获取锁{}成功,持有量：{}",lock4Reduce.getName(),lock4Reduce.getHoldCount());
            CommodityStockEntity stockEntity = stockRepo.findByCommodityCode(commodityCode);
            if(stockEntity == null){
                throw new EntityNotFoundException("指定的商品["+commodityCode+"]不存在！");
            }
            int inventory = stockEntity.getInventory();
            if(inventory == 0){
                logger.warn("商品不够了，需要加库存！");
                return 0;
            }
            return stockRepo.reduce(commodityCode);
        }finally {
            // 不管操作成功与否，最后都要是否锁
            if (lock4Reduce.isLocked()) {
                if (lock4Reduce.isHeldByCurrentThread()) {
                    lock4Reduce.unlock();
                }
            }
        }
    }

}