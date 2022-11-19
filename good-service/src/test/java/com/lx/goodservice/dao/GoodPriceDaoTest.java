package com.lx.goodservice.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.goodservice.pojo.GoodPrice;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest()
@RunWith(SpringRunner.class)
@Slf4j
public class GoodPriceDaoTest {
    @Autowired
    GoodPriceDao goodPriceDao;

    @Test
    public void insert() {
//        goodPriceDao.insert(GoodPrice.builder()
//                .id("3")
//                .price(10)
//                .isNew(false)
//                .updateTime(new Date())
//                .build());
    }
    @Test
    public void select(){
        QueryWrapper<GoodPrice> queryWrapper = new QueryWrapper<>();
        List<GoodPrice> goodPrices = goodPriceDao.selectList(queryWrapper);
        goodPrices.forEach(System.out::println);
    }
}