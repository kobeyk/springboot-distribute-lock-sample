package com.appleyk.redis.controller;

import com.appleyk.common.result.Result;
import com.appleyk.redis.service.CommodityStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/reduce/{commodityCode}")
    public Result commodityReduce(@PathVariable("commodityCode") String commodityCode) throws Exception{
        int reduce = commodityReduce.reduce(commodityCode);
        if(reduce>0){
            return Result.ok("减库存成功！");
        }else{
            logger.info("商品{}已售罄",commodityCode);
            return Result.ok("商品已售罄！");
        }
    }

    @GetMapping("/reduce/lock/{commodityCode}")
    public Result commodityReduceLock(@PathVariable("commodityCode") String commodityCode) throws Exception{
        int reduce = commodityReduce.reduceLock(commodityCode);
        if(reduce>0){
            return Result.ok("减库存成功！");
        }else{
            logger.info("商品{}已售罄",commodityCode);
            return Result.ok("商品已售罄！");
        }
    }

    @GetMapping("/reduce2/{commodityCode}")
    public Result commodityReduce2(@PathVariable("commodityCode") String commodityCode) throws Exception{
        int reduce = commodityReduce.reduce2(commodityCode);
        if(reduce>0){
            return Result.ok("减库存成功！");
        }else{
            logger.info("商品{}已售罄",commodityCode);
            return Result.ok("商品已售罄！");
        }
    }

}
