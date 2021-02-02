package com.appleyk.zk.dao.mapper;

import com.appleyk.zk.dao.entity.CommodityEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>通用mapper接口（mybatis底层用的是代理）</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 16:56 2021/2/1
 */
@org.apache.ibatis.annotations.Mapper
public interface CommodityMapper extends Mapper<CommodityEntity> {

    @Update("update t_commodity_stock set inventory = inventory - 1 where commodity_code = #{cCode}")
    int reduce(@Param("cCode") String cCode);

}
