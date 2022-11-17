package com.lx.goodservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lx.common.base.BaseService;
import com.lx.goodservice.dao.GoodInfoDao;
import com.lx.goodservice.dto.AddGoodDTO;
import com.lx.goodservice.pojo.GoodDetail;
import com.lx.goodservice.pojo.GoodInfo;
import com.lx.goodservice.pojo.GoodPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LIXIN
 */
@Service
@Slf4j
public class GoodInfoService extends BaseService<GoodInfo, GoodInfoDao> {
    @Autowired
    GoodPriceService goodPriceService;
    @Autowired
    GoodDetailService goodDetailService;

    @Transactional(rollbackFor = Exception.class)
    public int addGoodInfo(AddGoodDTO dto) {
        GoodInfo goodInfo = GoodInfo.builder()
                .goodName(dto.getGoodName())
                .goodImage(dto.getPic())
                .stoke(Long.valueOf(dto.getGoodStoke())).build();
        int a = super.insert(goodInfo);
        if (a < 0) return a;
        GoodPrice price = GoodPrice.builder()
                .price(dto.getGoodPrice())
                .isNew(true)
                .goodId(goodInfo.getId()).build();
        int b = goodPriceService.insert(price);
        if (b < 0) return b;
        GoodDetail detail = GoodDetail.builder()
                .id(goodInfo.getId())
                .goodIntroduce(dto.getGoodIntroduce())
                .catOneLevel(dto.getGoodCat()).build();
        return goodDetailService.insert(detail);
    }

    @Override
    public IPage<GoodInfo> selectPage(GoodInfo goodInfo, QueryWrapper<GoodInfo> wrapper) {
        return super.selectPage(goodInfo,wrapper);
    }
}
