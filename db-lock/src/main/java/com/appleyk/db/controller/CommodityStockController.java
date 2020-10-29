package com.appleyk.db.controller;


import com.appleyk.common.result.Result;
import com.appleyk.db.nolock.NoLockService;
import com.appleyk.db.optimistic.OLockService;
import com.appleyk.db.pessimistic.基于表记录行锁.PLockService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器 -- 商品减库存(分三种模式，来模拟并发下商品的减库存操作)
 * </p>
 *
 * @author Appleyk
 * @since 2020-10-21
 */
@RestController
@RequestMapping("/commodity/stock")
public class CommodityStockController {

    @Autowired
    private NoLockService noLockService;

    @Autowired
    private PLockService2 lockService2;

    @Autowired
    private OLockService oLockService;

    // 1、不加锁，裸奔减库存
    @GetMapping("/reduce/{commodityCode}")
    public Result nLockCommodityReduce(@PathVariable("commodityCode") String commodityCode) throws Exception{
        boolean bRes = noLockService.commodityReduce(commodityCode);
        if(bRes){
            return Result.ok("减库存成功！");
        }else{
            return Result.ok("商品已售罄！");
        }
    }

    // 2、使用悲观锁（for update），实现减库存
    @GetMapping("/pessimistic/lock/reduce/{commodityCode}")
    public Result pLockCommodityReduce(@PathVariable("commodityCode") String commodityCode)
     throws Exception{
        boolean bRes = lockService2.commodityReduce(commodityCode);
        if(bRes){
            return Result.ok("减库存成功！");
        }else{
            return Result.ok("商品已售罄！");
        }
    }

    // 3、使用乐观锁（版本号 or 时间戳），实现减库存
    @GetMapping("/optimistic/lock/reduce/{commodityCode}")
    public Result oLockCommodityReduce(@PathVariable("commodityCode") String commodityCode)
            throws Exception{
        boolean bRes = oLockService.commodityReduce(commodityCode);
        if(bRes){
            return Result.ok("减库存成功！");
        }else{
            return Result.ok("商品已售罄！");
        }
    }

}

