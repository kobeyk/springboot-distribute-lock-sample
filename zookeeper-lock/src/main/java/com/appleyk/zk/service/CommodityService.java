package com.appleyk.zk.service;

/**
 * <p>商品减库存业务接口</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 17:33 2021/2/1
 */
public interface CommodityService {

    /**
     * 裸奔，直接减库存
     * @param commodityCode 商品编码
     * @return update影响的行数
     */
    Integer reduce(String commodityCode);

    /**
     * 基于zk框架的减库存操作，很简单，上来就是干，没有带什么version一说
     * @param commodityCode 商品编码
     * @return update影响的行数
     */
    Integer reduceLock(String commodityCode);

}
