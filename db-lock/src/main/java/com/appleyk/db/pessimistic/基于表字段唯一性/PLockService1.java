package com.appleyk.db.pessimistic.基于表字段唯一性;

import com.appleyk.db.service.TResourceLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * <p></p>
 *
 * @author Appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk/dubbo-spring-boot-sample
 * @date created on 11:13 2020/10/21
 */
@Service
public class PLockService1 {

    @Autowired
    private TResourceLockService lockService;

    @Transactional(rollbackFor = {SQLException.class,Exception.class})
    public boolean business(){

        // 首先要插入

        return true;

    }

}
