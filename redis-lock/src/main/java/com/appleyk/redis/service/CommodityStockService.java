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

    // 一切正常来
    Integer reduceLock0(String commodityCode);

    // 方法上不加@Transaction
    Integer reduceLock1(String commodityCode);

    // 改成事务提交成功后再释放锁
    Integer reduceLock2(String commodityCode);

    // 手动管理事务的提交和回滚
    Integer reduceLock3(String commodityCode);

    // 不加锁，将锁移至上层（如Controller），在上层进行粗粒度的加锁
    Integer reduceLock4(String commodityCode);
}
