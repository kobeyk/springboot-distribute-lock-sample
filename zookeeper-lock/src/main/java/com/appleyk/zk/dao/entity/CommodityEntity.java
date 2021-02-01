package com.appleyk.zk.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * <p></p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 16:55 2021/2/1
 */
@Table(name = "t_commodity_stock")
@Data
@AllArgsConstructor
public class CommodityEntity {

    @Id
    private Long id;

    /**
     * 商品编码
     */
    private String commodityCode;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品库存量
     */
    private Integer inventory;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改数据
     */
    private LocalDateTime updateTime;


}
