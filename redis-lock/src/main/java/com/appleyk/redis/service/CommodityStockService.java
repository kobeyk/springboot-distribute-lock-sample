package com.appleyk.redis.service;

/**
 * <p>
 *  商品库存业务接口
 * </p>
 *
 * @author Appleyk
 * @since 2020-10-21
 */
public interface CommodityStockService  {

    /**
     * 裸奔，直接减库存
     * @param commodityCode 商品编码
     * @return update影响的行数
     */
    Integer reduce(String commodityCode);

    /**
     * 基于redisson框架的减库存操作，很简单，上来就是干，没有带什么version一说
     * @param commodityCode 商品编码
     * @return update影响的行数
     */
    Integer reduceLock(String commodityCode);

    /**
     * 基于jpa的save方法，测试一下是否它也是线程安全的？
     * @param commodityCode 商品编码
     * @return update影响的行数
     */
    Integer reduce2(String commodityCode);

}
