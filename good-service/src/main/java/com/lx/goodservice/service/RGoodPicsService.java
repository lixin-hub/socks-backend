package com.lx.goodservice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.common.base.BaseService;
import com.lx.goodservice.dao.GoodAttributeDao;
import com.lx.goodservice.dao.RGoodPicsDao;
import com.lx.goodservice.pojo.GoodAttribute;
import com.lx.goodservice.pojo.GoodPrice;
import com.lx.goodservice.pojo.RGoodPics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RGoodPicsService extends ServiceImpl<RGoodPicsDao,RGoodPics> {
    @Autowired
    RGoodPicsDao rGoodPicsDao;

    public int insertBatchSomeColumn(List<RGoodPics> rGoodPics) {
        return rGoodPicsDao.insertBatchSomeColumn(rGoodPics);
    }
}
