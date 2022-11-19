package com.lx.orderservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lx.common.pojo.orderservice.GoodInfo;
import com.lx.orderservice.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderServiceTest {
@Autowired
OrderService service;
    @Test
    public void selectPage() {
        IPage<Order> orderIPage = service.selectPage(Order.builder().build(), null);
        orderIPage.getRecords().forEach(System.out::println);
    }

    @Test
    public void selectOrderGood() {
//        List<GoodInfo> goodInfos = service.selectOrderGood("1");
    }
}