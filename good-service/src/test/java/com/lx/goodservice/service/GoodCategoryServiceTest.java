package com.lx.goodservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.lx.goodservice.pojo.GoodCategory;
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
public class GoodCategoryServiceTest {

    @Autowired
    GoodCategoryService goodCategoryService;

    @Test
    public void testTree() {
        List<GoodCategory> tree = goodCategoryService.getTree("0",new PageDto<>(1,2));
        List<GoodCategory> tree1 = goodCategoryService.getTree("1",new PageDto<>(1,10));
//        each(tree);
        log.info(String.valueOf(tree));
        log.info(String.valueOf(tree1));
    }

    public void each(List<GoodCategory> categories) {
        for (GoodCategory child : categories) {
            System.out.println(child.getName());
            if (child.getChildren()!=null){
             each(child.getChildren());
            }
        }
    }
}