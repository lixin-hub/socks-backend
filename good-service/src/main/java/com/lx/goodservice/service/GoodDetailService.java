package com.lx.goodservice.service;

import com.lx.goodservice.dao.GoodDetailDao;
import com.lx.goodservice.dao.GoodInfoDao;
import com.lx.goodservice.pojo.GoodDetail;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

@Service
public class GoodDetailService extends BaseService<GoodDetail, GoodDetailDao> {
}
