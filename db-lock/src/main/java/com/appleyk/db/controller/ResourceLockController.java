package com.appleyk.db.controller;


import com.appleyk.common.result.Result;
import com.appleyk.db.pessimistic.基于表字段唯一性.PLockService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器 -- 基于悲观锁进行
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


    // 悲观锁，基于表字段唯一性约束
    @GetMapping("/pressmistic/lock/fetch1/{commoditycode}")
    public Result pLockfetch1(@PathVariable("commoditycode") String commodityCode) throws Exception{
        boolean business = service1.commodityReduce1(commodityCode);
        if(business){
            return Result.ok("获取锁成功！");
        }else{
            return Result.ok("获取锁失败（超时）！");
        }
    }

    // 悲观锁，基于表字段唯一性约束
    @GetMapping("/pressmistic/lock/fetch2/{commoditycode}")
    public Result pLockfetch2(@PathVariable("commoditycode") String commodityCode) throws Exception{
        boolean business = service1.commodityReduce2(commodityCode);
        if(business){
            return Result.ok("获取锁成功！");
        }else{
            return Result.ok("获取锁失败（超时）！");
        }
    }

    // 悲观锁，基于表字段唯一性约束
    @GetMapping("/test")
    public Result test() throws Exception{
        return Result.ok("from -- "+port);
    }


}

