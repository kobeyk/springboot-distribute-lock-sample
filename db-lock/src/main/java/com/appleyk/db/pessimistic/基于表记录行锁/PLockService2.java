package com.appleyk.db.pessimistic.基于表记录行锁;

import com.appleyk.common.excep.MybatisPlusException;
import com.appleyk.db.dao.entity.CommodityStock;
import com.appleyk.db.service.CommodityStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * <p>还是以减库存为例，基于select * from  .. for update</p>
 *
 * @author Appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk/dubbo-spring-boot-sample
 * @date created on 13:43 2020/10/27
 */
@Service
public class PLockService2 {

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private CommodityStockService stockService;

    @Transactional(rollbackFor = {SQLException.class,Exception.class})
    public boolean commodityReduce(String commodityCode) throws Exception {
        // 先使用行锁或者表锁
        CommodityStock stock = stockService.findByCodeForUpdate(commodityCode);
        System.out.printf("%s : %d 获取锁",Thread.currentThread().getName(),port);
        System.out.println();
        if(stock == null){
            throw new MybatisPlusException("商品不存在！");
        }

        if(stock.getInventory() == 0){
            return false;
        }

        // 然后执行减库存
        return stockService.reduce(commodityCode)>0;
    }
}
