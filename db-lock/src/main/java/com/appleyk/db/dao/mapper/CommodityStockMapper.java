package com.appleyk.db.dao.mapper;

import com.appleyk.db.dao.entity.CommodityStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Appleyk
 * @since 2020-10-21
 */
public interface CommodityStockMapper extends BaseMapper<CommodityStock> {

    Integer reduce(@Param("code") String commodityCode);

}
