package com.appleyk.db.nolock;

import com.appleyk.db.dao.entity.CommodityStock;
import com.appleyk.db.service.CommodityStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * <p>无锁实现并发下商品减库存</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk/dubbo-spring-boot-sample
 * @date created on 9:56 2020/10/29
 */
@Service
public class NoLockService {

    @Autowired
    private CommodityStockService stockService;

    @Value("${server.port}")
    private Integer port;

    /***
     * 商品减库存 (不带任何锁，毫无疑问，比如会出现超卖现象)
     */
    @Transactional(rollbackFor = {SQLException.class,Exception.class})
    public boolean commodityReduce(String commodityCode) throws Exception {

        CommodityStock commodityStock = stockService.findByCode(commodityCode);
        if(commodityStock.getInventory() == 0){
            System.out.println("商品不够了，需要加库存！");
            return false;
        }
        stockService.reduce(commodityCode);
        return true;

    }
}
