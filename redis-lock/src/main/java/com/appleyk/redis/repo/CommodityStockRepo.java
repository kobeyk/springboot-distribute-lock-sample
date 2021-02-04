package com.appleyk.redis.repo;

import com.appleyk.redis.entity.CommodityStockEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>商品库存repo（dao层）</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 14:54 2021/1/29
 */
public interface CommodityStockRepo extends PagingAndSortingRepository<CommodityStockEntity,Long> {

    CommodityStockEntity findByCommodityCode(String commodityCode);

    @Modifying
    @Query("update CommodityStockEntity cs set cs.inventory = :inventory where cs.commodityCode=:commodityCode")
    Integer reduce(@Param("commodityCode") String commodityCode, @Param("inventory") Integer inventory);

    @Transactional
    @Modifying
    @Query(value="update CommodityStockEntity cs set cs.inventory = cs.inventory-1 where cs.commodityCode=:commodityCode")
    Integer reduce(@Param("commodityCode") String commodityCode);

    @Modifying
    @Query(value="update CommodityStockEntity cs set cs.inventory = cs.inventory-1 where cs.commodityCode=:commodityCode")
    Integer reduce2(@Param("commodityCode") String commodityCode);
}
