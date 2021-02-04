package com.appleyk.redis.controller;

import com.appleyk.common.result.Result;
import com.appleyk.redis.service.CommodityStockService;
import com.appleyk.redis.utils.RedisDistributeLock;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>基于redisson框架的高并发下商品减库存的解决方案的测试</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 15:26 2021/1/29
 */
@RestController
@RequestMapping("/redis/commodity/stock")
public class CommodityController {

    private Logger logger = LoggerFactory.getLogger(CommodityController.class);

    @Autowired
    private CommodityStockService commodityReduce;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private RedisDistributeLock distributeLock;


    // 使用redisson分布式锁(正常流程走，这个是有问题的)
    @GetMapping("/reduce/lock0/{commodityCode}")
    public Result commodityReduceLock0(@PathVariable("commodityCode") String commodityCode) throws Exception{
        int reduce = commodityReduce.reduceLock0(commodityCode);
        if(reduce>0){
            return Result.ok("减库存成功！");
        }else{
            logger.info("商品{}已售罄",commodityCode);
            return Result.ok("商品已售罄！");
        }
    }

    // 使用redisson分布式锁(手动管理Spring事务的提交)
    @GetMapping("/reduce/lock/{commodityCode}")
    public Result commodityReduceLock(@PathVariable("commodityCode") String commodityCode) throws Exception{
        int reduce = commodityReduce.reduceLock1(commodityCode);
        if(reduce>0){
            return Result.ok("减库存成功！");
        }else{
            logger.info("商品{}已售罄",commodityCode);
            return Result.ok("商品已售罄！");
        }
    }

    // 使用redisson分布式锁(约束在事务执行成后供在释放锁)
    @GetMapping("/reduce/lock2/{commodityCode}")
    public Result commodityReduceLock2(@PathVariable("commodityCode") String commodityCode) throws Exception{
        int reduce = commodityReduce.reduceLock2(commodityCode);
        if(reduce>0){
            return Result.ok("减库存成功！");
        }else{
            logger.info("商品{}已售罄",commodityCode);
            return Result.ok("商品已售罄！");
        }
    }

    // 使用redisson分布式锁(手动管理Spring事务的提交)
    @GetMapping("/reduce/lock3/{commodityCode}")
    public Result commodityReduceLock3(@PathVariable("commodityCode") String commodityCode) throws Exception{
        int reduce = commodityReduce.reduceLock3(commodityCode);
        if(reduce>0){
            return Result.ok("减库存成功！");
        }else{
            logger.info("商品{}已售罄",commodityCode);
            return Result.ok("商品已售罄！");
        }
    }

    // 使用redisson分布式锁(将service的锁粒度提到controller，远离@Transactional（aop），使得事务提交一定发生在锁释放之前)
    @GetMapping("/reduce/lock4/{commodityCode}")
    public Result commodityReduceLock4(@PathVariable("commodityCode") String commodityCode) throws Exception{
        RLock lock4Reduce = distributeLock.lock4Reduce(commodityCode);
        try{
            int reduce = commodityReduce.reduceLock4(commodityCode);
            if(reduce>0){
                return Result.ok("减库存成功！");
            }else{
                logger.info("商品{}已售罄",commodityCode);
                return Result.ok("商品已售罄！");
            }
        }finally {
            if (lock4Reduce.isLocked()) {
                if (lock4Reduce.isHeldByCurrentThread()) {
                    lock4Reduce.unlock();
                }
            }
        }
    }

}
