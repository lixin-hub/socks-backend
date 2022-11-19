package com.lx.orderservice.service;

import com.lx.common.base.BaseService;
import com.lx.common.pojo.orderservice.GoodInfo;
import com.lx.orderservice.dao.OrderDao;
import com.lx.orderservice.dao.ROrderGoodDao;
import com.lx.orderservice.pojo.Order;
import com.lx.orderservice.pojo.ROrderGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.List;

@Service
public class ROrderGoodService extends BaseService<ROrderGood, ROrderGoodDao> {

}
