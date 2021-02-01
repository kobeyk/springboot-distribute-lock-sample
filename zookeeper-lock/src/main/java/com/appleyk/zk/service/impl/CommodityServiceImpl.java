package com.appleyk.zk.service.impl;

import com.appleyk.zk.service.CommodityService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>商品减库存业务接口实现类</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 17:43 2021/2/1
 */
@Service
public class CommodityServiceImpl implements CommodityService {

    private static Logger logger = LoggerFactory.getLogger(CommodityServiceImpl.class);

    @Autowired
    private CuratorFramework curatorFramework;

    private String lockPath = "/lock/test/";

    @Override
    public Integer reduce(String commodityCode) {
        return null;
    }

    @Override
    public Integer reduceLock(String commodityCode) {
        String lockName = lockPath + UUID.randomUUID().toString();
        logger.info("============={} 线程访问开始=========lockName:{}",Thread.currentThread().getName(),lockName);
        // recipes不可重入的互斥锁
        InterProcessSemaphoreMutex lock = new InterProcessSemaphoreMutex (curatorFramework, lockName);
        try{
            //获取锁资源
            boolean flag = lock.acquire(10, TimeUnit.HOURS);
            if(flag){
                logger.info("线程:{}，获取到了锁",Thread.currentThread().getName());
                //TODO 获得锁之后可以进行相应的处理  睡一会
                Thread.sleep(500);
                logger.info("======获得锁后进行相应的操作======" + Thread.currentThread().getName());
            }
        }catch (Exception e){
            logger.info("错误信息：{}",e.getMessage());
        }finally {
            try {
                lock.release();
                logger.info("=========lockName:{}==============={}释放了锁",lockName,Thread.currentThread().getName());
            } catch (Exception e) {
                logger.info("错误信息：{}",e.getMessage());
            }
        }
        return null;
    }
}
