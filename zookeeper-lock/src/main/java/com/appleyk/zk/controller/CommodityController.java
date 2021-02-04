package com.appleyk.zk.controller;

import com.appleyk.common.result.Result;
import com.appleyk.zk.service.CommodityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>基于zookeeper的顺序临时节点来实现分布式锁</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 17:16 2021/2/1
 */
@RestController
@RequestMapping("/zk/commodity/stock")
@Api(tags = "1、基于ZK分布式锁的减库存接口")
public class CommodityController {

    private Logger logger = LoggerFactory.getLogger(CommodityController.class);

    @Autowired
    private CommodityService commodityReduce;

    @ApiOperation("不加锁，实现高并发减库存")
    @GetMapping("/reduce/{commodityCode}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commodityCode", value = "商品编码", defaultValue = "CN100124512")
    })
    public Result commodityReduce(@PathVariable("commodityCode") String commodityCode) throws Exception{
        int reduce = commodityReduce.reduce(commodityCode);
        if(reduce>0){
            return Result.ok("减库存成功！");
        }else{
            logger.info("商品{}已售罄",commodityCode);
            return Result.ok("商品已售罄！");
        }
//        return Result.ok("成功");
    }

    @ApiOperation("加zk分布式锁，实现高并发减库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commodityCode", value = "商品编码", defaultValue = "CN100124512")
    })
    @GetMapping("/lock/reduce/{commodityCode}")
    public Result commodityReduceByLock(@PathVariable("commodityCode") String commodityCode) throws Exception{
        int reduce = commodityReduce.reduceLock(commodityCode);
        if(reduce>0){
            return Result.ok("减库存成功！");
        }else{
            logger.info("商品{}已售罄",commodityCode);
            return Result.ok("商品已售罄！");
        }
    }
}
