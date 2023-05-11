package com.lx.goodservice.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.goodservice.dao.GoodPriceDao;
import com.lx.goodservice.pojo.GoodPrice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @author LIXIN
 */
@Service
public class GoodPriceService extends ServiceImpl<GoodPriceDao, GoodPrice> {
    public GoodPrice getByGoodId(Serializable id) {
        return lambdaQuery().eq(GoodPrice::getGoodId,id).eq(GoodPrice::getIsNew, GoodPrice.NEW).one();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(GoodPrice entity) {
         deprecatedPrice(entity.getGoodId());
        return super.save(entity);
    }

    @Override
    public GoodPrice getById(Serializable id) {
        return lambdaQuery().eq(GoodPrice::getIsNew, GoodPrice.NEW).one();
    }

    /**
     * @param goodPrice 最新的价格
     * @return 是否成功
     * 如果价格不相同先将所有价格过期然后插入新的价格
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAndSavePriceByGoodId(GoodPrice goodPrice) {
        GoodPrice byGoodId = getByGoodId(goodPrice.getGoodId());
        double price = byGoodId.getPrice();
        double price1 = goodPrice.getPrice();
        //价格相同不用更新
        if (Math.abs(price - price1) < 0.001) {
            return true;
        }
        if (deprecatedPrice(goodPrice.getGoodId())) {
            //插入新的价格
            return save(goodPrice);
        }
        return false;
    }

    //作废所有价格
    private boolean deprecatedPrice(String goodId) {
        return lambdaUpdate().eq(GoodPrice::getIsNew, GoodPrice.NEW).eq(GoodPrice::getGoodId, goodId).update(GoodPrice.builder().isNew(GoodPrice.NOT_NEW).build());
    }
}
