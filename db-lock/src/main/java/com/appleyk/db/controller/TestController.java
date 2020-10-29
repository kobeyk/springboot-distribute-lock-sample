package com.appleyk.db.controller;

import com.appleyk.common.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>测试API</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk/dubbo-spring-boot-sample
 * @date created on 10:04 2020/10/29
 */
@CrossOrigin
@RestController
@RequestMapping("/dl")
public class TestController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/test")
    public Result test(){
        return Result.ok("from -- "+port);
    }
}
