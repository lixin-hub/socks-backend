package com.lx.orderservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lx.common.base.BaseService;
import com.lx.common.base.Result;
import com.lx.common.pojo.orderservice.GoodInfo;
import com.lx.orderservice.dao.OrderDao;
import com.lx.orderservice.pojo.Order;
import com.lx.orderservice.pojo.ROrderGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService extends BaseService<Order, OrderDao> {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ROrderGoodService rOrderGoodService;


    public List<ROrderGood> selectOrderGood(String id)  {
        List<ROrderGood> rOrderGood = rOrderGoodService.selectList(ROrderGood.builder().orderId((String) id).build());
        if (rOrderGood.size()==0)throw new IllegalArgumentException("找不到该订单关联的商品");
        List<String> ids = new ArrayList<>();
        rOrderGood.forEach(i -> ids.add(i.getGoodId()));
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        String json = JSON.toJSONString(ids);
        HttpEntity<String> formEntity = new HttpEntity<String>(json, headers);
        System.out.println(json);
        String res = restTemplate.postForObject("http://good-service/good/listId", formEntity, String.class);
//        System.out.println(res);
//        JSONObject result = JSONObject.parseObject(res);
//        JSONArray data = result.getJSONArray("data");
//        List<GoodInfo> goodInfos = data.toJavaList(GoodInfo.class);
        List<GoodInfo> goodInfos = restListData(res, GoodInfo.class);
        goodInfos.forEach(System.out::println);
        HashMap<String, GoodInfo> hashMap=new HashMap<>();
        for (GoodInfo datum : goodInfos) {
            hashMap.put(datum.getId(),datum);
        }
        for (ROrderGood orderGood : rOrderGood) {
            orderGood.setGoodInfo(hashMap.get(orderGood.getGoodId()));
        }
        return rOrderGood;
    }
}
