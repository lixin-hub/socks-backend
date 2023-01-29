package com.lx.goodservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lx.common.base.BaseService;
import com.lx.goodservice.dao.GoodInfoDao;
import com.lx.goodservice.dao.RGoodAttributeDao;
import com.lx.goodservice.dto.AddGoodDTO;
import com.lx.goodservice.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    RGoodPicsService rGoodPicsService;

    @Resource
    RGoodAttributeDao rGoodAttributeDao;

    /**
     * 保存商品信息
     */
    @Transactional(rollbackFor = Exception.class)
    public int addGoodInfo(AddGoodDTO dto) {
        GoodInfo goodInfo = GoodInfo.builder()
                .goodName(dto.getGoodName())
                .stoke(Long.valueOf(dto.getGoodStoke())).build();
        int a = super.insert(goodInfo);
        if (a == 0) return a;
        GoodPrice price = GoodPrice.builder()
                .price(dto.getGoodPrice())
                .isNew(true)
                .goodId(goodInfo.getId()).build();
        int b = goodPriceService.insert(price);
        if (b == 0) return b;
        GoodDetail detail = GoodDetail.builder()
                .id(goodInfo.getId())
                .goodIntroduce(dto.getGoodIntroduce())
                .catOneLevel(dto.getGoodCat()).build();
        int e = goodDetailService.insert(detail);
        if (e == 0) return e;
        List<AddGoodDTO.Attr> attrs = dto.getAttrs();
        List<RGoodAttribute> rGoodAttributes = new ArrayList<>(10);
        attrs.forEach(attr -> {
            RGoodAttribute rGoodAttr = RGoodAttribute.builder()
                    .attrType(dto.getAttrType())
                    .attrId(attr.getAttrId())
                    .value(attr.getAttrValue())
                    .goodId(goodInfo.getId()).build();
            rGoodAttributes.add(rGoodAttr);
        });
        List<RGoodPics> rGoodPics = dto.getRGoodPics();
        for (RGoodPics rGoodPic : rGoodPics) {
            rGoodPic.setGoodId(goodInfo.getId());
            rGoodPic.setStatus("0");
            rGoodPicsService.save(rGoodPic);
        }
//        int d = rGoodPicsService.insertBatchSomeColumn(rGoodPics); //批量插入不能插入status？
//        if (d == 0) return e;
        return rGoodAttributeDao.insertBatchSomeColumn(rGoodAttributes);
    }

    @Override
    public IPage<GoodInfo> selectPage(GoodInfo goodInfo, QueryWrapper<GoodInfo> wrapper) {
        IPage<GoodInfo> page = super.selectPage(goodInfo, wrapper);
        page.getRecords().forEach(info -> {
            String id = info.getId();
            List<RGoodPics> list = rGoodPicsService.lambdaQuery().eq(RGoodPics::getGoodId, id).list();
            info.setGoodPics(list);
        });
        return page;
    }

    @Override
    public GoodInfo selectById(Serializable id) {
        return super.selectById(id);
    }
}
