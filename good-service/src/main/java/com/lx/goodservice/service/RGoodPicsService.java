package com.lx.goodservice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.goodservice.dao.RGoodPicsDao;
import com.lx.goodservice.pojo.RGoodPics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RGoodPicsService extends ServiceImpl<RGoodPicsDao, RGoodPics> {
    @Autowired
    RGoodPicsDao rGoodPicsDao;

    public int insertBatchSomeColumn(List<RGoodPics> rGoodPics) {
        return rGoodPicsDao.insertBatchSomeColumn(rGoodPics);
    }

    //根据商品id对图片进行更新
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByGoodId(String goodId, List<RGoodPics> rGoodPics) {
        //首先将之前的记录删除
        if (lambdaUpdate().eq(RGoodPics::getGoodId, goodId).remove())
            //保存新的记录
            return saveBatch(rGoodPics);
        return false;
    }
}
