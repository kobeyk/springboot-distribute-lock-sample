package com.appleyk.db.controller;


import com.appleyk.common.result.Result;
import com.appleyk.db.pessimistic.基于表记录行锁.PLockService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器 --
 * </p>
 *
 * @author Appleyk
 * @since 2020-10-21
 */
@RestController
@RequestMapping("/commodity/stock")
public class CommodityStockController {

    @Autowired
    private PLockService2 lockService2;

    @GetMapping("/pessmistic/lock/reduce/{commoditycode}")
    public Result commodityReduce(@PathVariable("commoditycode") String commodityCode)
     throws Exception{
        boolean bRes = lockService2.commodityReduce(commodityCode);
        if(bRes){
            return Result.ok("减库存成功！");
        }else{
            return Result.ok("商品已售罄！");
        }
    }

}

