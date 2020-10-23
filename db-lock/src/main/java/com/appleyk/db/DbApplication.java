package com.appleyk.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * <p>基于数据库，实现分布式锁 -- 启动类</p>
 *
 * @author Appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk/dubbo-spring-boot-sample
 * @date created on 13:23 2020/10/20
 */
@SpringBootApplication
public class DbApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DbApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DbApplication.class);
    }
}
