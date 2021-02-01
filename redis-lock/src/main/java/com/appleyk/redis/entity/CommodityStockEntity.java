package com.appleyk.redis.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *    商品库存表
 * </p>
 *
 * @author Appleyk
 * @since 2020-10-21
 */
@Entity
@Table(name = "t_commodity_stock")
public class CommodityStockEntity implements Serializable {

    private static final long serialVersionUID=1L;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TCommodityStock{" +
        "id=" + id +
        ", commodityCode=" + commodityCode +
        ", commodityName=" + commodityName +
        ", inventory=" + inventory +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
