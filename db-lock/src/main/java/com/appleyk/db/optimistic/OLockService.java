package com.appleyk.db.optimistic;

import com.appleyk.db.dao.entity.CommodityStock;
import com.appleyk.db.service.CommodityStockService;
import com.appleyk.db.service.ResourceLockService;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * <p>基于乐观锁（业务层面上实现）</p>
 *
 * @author Appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk/dubbo-spring-boot-sample
 * @date created on 14:51 2020/10/28
 */
@Service
public class OLockService {

    @Autowired
    private ResourceLockService lockService;

    @Autowired
    private CommodityStockService stockService;

    @Value("${server.port}")
    private Integer port;

    /***
     * 商品减库存（带锁，这种情况下，不会出现超卖现象，就是效率太低）
     */
    @Transactional(rollbackFor = {SQLException.class,Exception.class})
    public boolean commodityReduce(String commodityCode) throws Exception {

        // 1.先查
        CommodityStock commodityStock = stockService.findByCode(commodityCode);

        if(commodityStock == null){
            throw new MybatisPlusException("商品不存在！");
        }

        if(commodityStock.getInventory() == 0){
            System.out.println("商品已经售罄！");
            return false;
        }
        // 2.再把版本号保存下来
        long version = commodityStock.getVersion();

        // 3.执行减库存(看运气，谁能减成功，证明他操作的时候，没有其他线程修改库存)
        //   如果执行失败了，可以提示，系统繁忙等（要不就循环减库存，不建议这样搞）
        return stockService.reduce(commodityCode,version) > 0;

    }

}
