package com.appleyk.db.controller;


import com.appleyk.common.result.Result;
import com.appleyk.db.pessimistic.基于表字段唯一性.PLockService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器 -- 通过抢占方法（资源）的方式·悲观锁，实现商品的减库存操作
 * </p>
 *
 * @author Appleyk
 * @since 2020-10-21
 */
@CrossOrigin
@RestController
@RequestMapping("/resource")
public class ResourceLockController {

    @Autowired
    private PLockService1 service1;

    @Value("${server.port}")
    private Integer port;


    // 悲观锁，基于表字段唯一性约束，线程之间通过insert的方式抢占资源
    @GetMapping("/pessimistic/lock/reduce/{commodityCode}")
    public Result pLockReduceStock(@PathVariable("commodityCode") String commodityCode) throws Exception{
        boolean bRes = service1.commodityReduce(commodityCode);
        if(bRes){
            return Result.ok("减库存成功！");
        }else{
            return Result.ok("商品已售罄（也有可能是用户竞争太激烈，部分用户抢不到商品，系统处理了）！");
        }
    }
}

