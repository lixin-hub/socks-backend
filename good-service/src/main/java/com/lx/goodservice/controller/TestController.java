package com.lx.goodservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
//@RequestMapping("good")
@Slf4j
public class TestController {
    @Autowired
    RestTemplate restTemplate;
    @GetMapping("test/{goodId}")
    public Object test(@PathVariable("goodId") String goodId) {
        String userInfo = restTemplate.getForObject("http://user-service/test/001", String.class);
        log.debug("good-service");
        return "商品:" + goodId+"->用户："+userInfo;
    }
}
