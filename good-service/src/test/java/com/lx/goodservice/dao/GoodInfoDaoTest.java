package com.lx.goodservice.dao;


import com.lx.goodservice.pojo.GoodInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest()
@RunWith(SpringRunner.class)
@Slf4j
public class GoodInfoDaoTest {
    @Autowired
    GoodInfoDao goodInfoDao;
    @Test
   public void insert() {
    }
    @Test
    public void select(){

    }
}