package com.appleyk.db.service.impl;

import com.appleyk.db.dao.entity.CommodityStock;
import com.appleyk.db.dao.mapper.CommodityStockMapper;
import com.appleyk.db.service.CommodityStockService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Appleyk
 * @since 2020-10-21
 */
@Service
public class CommodityStockServiceImpl extends ServiceImpl<CommodityStockMapper, CommodityStock> implements CommodityStockService {

    @Autowired
    private CommodityStockMapper stockMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer reduce(String commodityCode) {
        return stockMapper.reduce(commodityCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer reduce(String commodityCode,long version) {
        return stockMapper.reduce2(commodityCode,version);
    }


    @Override
    public CommodityStock findByCode(String commodityCode) {
        QueryWrapper<CommodityStock> wrapper = new QueryWrapper<>();
        wrapper.eq("commodity_code",commodityCode);
        return stockMapper.selectOne(wrapper);
    }

    @Override
    public CommodityStock findByCodeForUpdate(String commodityCode) {
        return stockMapper.findByCode(commodityCode);
    }
}
