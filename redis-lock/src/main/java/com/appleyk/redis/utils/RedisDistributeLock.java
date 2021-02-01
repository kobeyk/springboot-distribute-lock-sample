package com.appleyk.redis.utils;

import org.redisson.api.RLock;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.LockTimeoutException;
import java.util.concurrent.TimeUnit;

/**
 * <p>基于redisson框架构建的分布式锁工具bean</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 15:01 2021/1/29
 */
@Component
public class RedisDistributeLock {

    // 所有商品操作的分布式锁统一放于一个set中
    private static final String COMMODITY_REDIS_LOCK_SET_NAME = "commodity";

    // 简单起见，商品减库存的锁后缀标识就叫lock
    private static final String COMMODITY_REDUCE_LOCK_IDENTIFIER = "lock";

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 获取指定商品的减库存锁
     * @param commodityCode 商品编码（唯一）
     * @return RLock
     */
    public RLock lock4Reduce(String commodityCode){
        RSet<String> lockSet = redissonClient.getSet(COMMODITY_REDIS_LOCK_SET_NAME);
        String locker =commodityCode+"_"+COMMODITY_REDUCE_LOCK_IDENTIFIER;
        RLock lock = lockSet.getLock(locker);
        try {
            if (!lock.tryLock(10, TimeUnit.SECONDS)) {
                throw new LockTimeoutException("获取商品减库存操作的锁超时!");
            }
//            System.out.println("线程："+Thread.currentThread().getName()+"获取锁成功！");
        } catch (InterruptedException e) {
            throw new RuntimeException("商品【"+ commodityCode+"】减少库存操作失败！");
        }
        return lock;
    }

}
