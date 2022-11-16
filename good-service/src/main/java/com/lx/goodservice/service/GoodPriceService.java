package com.lx.goodservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.common.base.BaseService;
import com.lx.goodservice.dao.GoodPriceDao;
import com.lx.goodservice.pojo.GoodPrice;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author LIXIN
 */
@Service
public class GoodPriceService extends BaseService<GoodPrice, GoodPriceDao> {
    public GoodPrice selectByGoodId(Serializable id) {
        return dao.selectOne(new QueryWrapper<GoodPrice>().eq("good_id", id).eq("is_new", "1"));
    }

    @Override
    public GoodPrice selectById(Serializable id) {
        return dao.selectOne(new QueryWrapper<GoodPrice>().eq("is_new", "1"));
    }
}
