package com.appleyk.db.service;

import com.appleyk.db.dao.entity.CommodityStock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Appleyk
 * @since 2020-10-21
 */
public interface CommodityStockService extends IService<CommodityStock> {

    Integer reduce(String commodityCode);

    CommodityStock findByCode(String commodityCode);

}
