package com.appleyk.redis.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
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
    @Column(name = "commodity_code")
    private String commodityCode;

    /**
     * 商品名称
     */
    @Column(name = "commodity_name")
    private String commodityName;

    /**
     * 商品库存量
     */
    private Integer inventory;


    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Timestamp createTime;

    /**
     * 修改数据
     */
    @Column(name = "update_time")
    private Timestamp updateTime;


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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
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
