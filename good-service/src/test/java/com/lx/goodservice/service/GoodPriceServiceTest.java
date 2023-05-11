package com.lx.goodservice.service;

import com.lx.goodservice.pojo.GoodPrice;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class GoodPriceServiceTest {
@Autowired
GoodPriceService goodPriceService;
    @Test
    public void getByGoodId() {
        GoodPrice byGoodId = goodPriceService.getByGoodId("88e5e03cb61a13c99ae2d0688a7b1a29");
        log.info(byGoodId.toString());
    }
}