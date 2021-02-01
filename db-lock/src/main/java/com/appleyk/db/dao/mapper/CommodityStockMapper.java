package com.appleyk.db.dao.mapper;

import com.appleyk.db.dao.entity.CommodityStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    Integer reduce2(@Param("code") String commodityCode,@Param("version") long version);

    @Select("select commodity_code, commodity_name, inventory from t_commodity_stock where " +
            "commodity_code = #{commodityCode} for update")
    CommodityStock findByCode(@Param("commodityCode") String commodityCode);

}
