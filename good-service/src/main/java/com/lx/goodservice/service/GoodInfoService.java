package com.lx.goodservice.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class GoodInfoService extends ServiceImpl<GoodInfoDao, GoodInfo> {
    @Autowired
    GoodPriceService goodPriceService;
    @Autowired
    GoodDetailService goodDetailService;
    @Autowired
    RGoodPicsService rGoodPicsService;
    @Autowired
    RGoodAttributeService rGoodAttributeService;
    @Resource
    RGoodAttributeDao rGoodAttributeDao;

    /**
     * 保存商品信息
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addGoodInfo(AddGoodDTO dto) {
        GoodInfo goodInfo = GoodInfo.builder()
                .goodName(dto.getGoodName())
                .block(dto.getBlock())
                .stoke(Long.valueOf(dto.getGoodStoke())).build();
        if (!super.save(goodInfo)) return false;
        GoodPrice price = GoodPrice.builder()
                .price(dto.getGoodPrice())
                .isNew(GoodPrice.NEW)
                .goodId(goodInfo.getId()).build();
        if (!goodPriceService.save(price)) return false;
        GoodDetail detail = GoodDetail.builder()
                .id(goodInfo.getId())
                .goodIntroduce(dto.getGoodIntroduce())
                .catOneLevel(dto.getGoodCat()).build();
        if (!goodDetailService.save(detail)) return false;
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
        return rGoodAttributeDao.insertBatchSomeColumn(rGoodAttributes) > 0;
    }

    @Override
    public <E extends IPage<GoodInfo>> E page(E page, Wrapper<GoodInfo> queryWrapper) {
        E mPage = super.page(page, queryWrapper);
        mPage.getRecords().forEach(info -> {
            String id = info.getId();
            List<RGoodPics> list = rGoodPicsService.lambdaQuery().eq(RGoodPics::getGoodId, id).list();
            info.setGoodPics(list);
        });
        return mPage;
    }

    public GoodInfo selectById(Serializable id) {
        GoodInfo goodInfo = getById(id);
        List<RGoodPics> list = rGoodPicsService.lambdaQuery().eq(RGoodPics::getGoodId, id).list();
        goodInfo.setGoodPics(list);
        return goodInfo;
    }

    //更新商品
    @Transactional(rollbackFor = Exception.class)
    public boolean updateGoodInfo(AddGoodDTO dto) {
        //更新商品
        GoodInfo goodInfo = GoodInfo.builder()
                .id(dto.getId())
                .block(dto.getBlock())
                .goodName(dto.getGoodName())
                .stoke(Long.valueOf(dto.getGoodStoke())).build();
        boolean b = super.updateById(goodInfo);
        if (!b) return false;
        //更新价格
        if (!goodPriceService.updateAndSavePriceByGoodId(GoodPrice.builder()
                .price(dto.getGoodPrice())
                .goodId(dto.getId())
                .isNew(GoodPrice.NEW).build()))
            return false;
        //更新商品详情
        if (goodDetailService.updateById(GoodDetail.builder()
                .id(goodInfo.getId())
                .goodIntroduce(dto.getGoodIntroduce())
                .catOneLevel(dto.getGoodCat()).build()))
            return false;
//        更新属性
        List<AddGoodDTO.Attr> attrs = dto.getAttrs();
        List<RGoodAttribute> rGoodAttributes = new ArrayList<>(10);
        attrs.forEach(attr -> rGoodAttributes.add(RGoodAttribute.builder()
                .attrType(dto.getAttrType())
                .attrId(attr.getAttrId())
                .value(attr.getAttrValue())
                .goodId(goodInfo.getId()).build()));
        if (!rGoodAttributeService.updateBatchById(rGoodAttributes)) return false;
        //更新图片
        List<RGoodPics> rGoodPics = dto.getRGoodPics();
        return rGoodPicsService.updateByGoodId(dto.getId(), rGoodPics);
    }
}
