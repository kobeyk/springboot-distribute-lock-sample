package com.appleyk.db.pessimistic.基于表字段唯一性;

import com.appleyk.common.excep.MybatisPlusException;
import com.appleyk.db.dao.entity.CommodityStock;
import com.appleyk.db.dao.entity.ResourceLock;
import com.appleyk.db.service.CommodityStockService;
import com.appleyk.db.service.ResourceLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * <p></p>
 *
 * @author Appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk/dubbo-spring-boot-sample
 * @date created on 11:13 2020/10/21
 */
@Service
public class PLockService1 {

    @Autowired
    private ResourceLockService lockService;

    @Autowired
    private CommodityStockService stockService;

    @Value("${server.port}")
    private Integer port;

    /***
     * 商品减库存（带锁，这种情况下，不会出现超卖现象，就是效率太低）
     */
    @Transactional(rollbackFor = {SQLException.class,Exception.class})
    public boolean commodityReduce1(String commodityCode) throws Exception {

        ResourceLock lock = new ResourceLock();
        lock.setResourcName("PLockService1.business");
        lock.setThreadName(Thread.currentThread().getName());
        lock.setServerAddress("localhost:"+port);
        lock.setCreateTime(LocalDateTime.now());

        // 首先要插入
        boolean bSave  =false;
        // 如果插入失败，意味着获取资源锁失败，那就一直阻塞去插入(获取锁，重复次数10)
        int i = 0;
        while(!bSave){
            try{
                System.out.println(Thread.currentThread().getName()+":"+port+",准备获取锁...");
                bSave  = lockService.save(lock);
            }catch (Exception e){
                System.err.println(Thread.currentThread().getName()+":"+port+",获取锁失败...,"+
                        e.getMessage());
            }
            i++;
            Thread.sleep(10);
            if(i == 3){
                throw new MybatisPlusException("获取锁的重试次数已到，任务终止！");
            }
        }
        System.out.println("获取锁成功！");
        // 如果跳出循环就是true，说明获取锁成功，开始执行业务
        // 先查
        CommodityStock commodityStock = stockService.findByCode(commodityCode);
        if(commodityStock.getInventory() == 0){
            System.out.println("商品不够了，需要加库存！");
            return lockService.removeById(lock.getId());
        }
        stockService.reduce(commodityCode);
        // 完成后，删除记录
        return lockService.removeById(lock.getId());

    }

    /***
     * 商品减库存 (不带任何锁，毫无疑问，比如会出现超卖现象)
     */
    @Transactional(rollbackFor = {SQLException.class,Exception.class})
    public boolean commodityReduce2(String commodityCode) throws Exception {

        CommodityStock commodityStock = stockService.findByCode(commodityCode);
        if(commodityStock.getInventory() == 0){
            System.out.println("商品不够了，需要加库存！");
            throw new MybatisPlusException("商品不够了，需要加库存！");
        }
        stockService.reduce(commodityCode);

        return true;

    }

}
