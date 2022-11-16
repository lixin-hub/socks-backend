package com.lx.goodservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.lx.goodservice.dao.GoodInfoDao;
import com.lx.goodservice.pojo.GoodInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest()
@RunWith(SpringRunner.class)
@Slf4j
public class GoodInfoServiceTest {

    @Autowired
    GoodInfoService service;
    @Autowired
    GoodInfoDao goodInfoDao;

    @Test
    public void selectById(){
        GoodInfo goodInfo = service.selectById("2");
        System.out.println(goodInfo);
    }
    @Test
    public void list() {
        List<GoodInfo> goodInfos = service.selectList(GoodInfo.builder().id("1").build());
        assertNotNull(goodInfos);
        goodInfos.forEach(System.out::println);
    }

    @Test
    public void page() {
        GoodInfo nike = GoodInfo.builder().goodName("人")
                .build().queryType("like");
        nike.setPage(new PageDto<>(1, 20));
        IPage<GoodInfo> page = service.selectPage(nike,nike.normalWrapper());
        page.getRecords().forEach(System.out::println);
    }

    @Test
    public void delete() {
        int re = service.delete(GoodInfo.builder().goodName("哈哈哈").build());
        assertTrue(re > 0);
    }

    @Test
    public void insert() {
        int re = service.insert(GoodInfo.builder().goodName("哈哈哈").build());
        assertTrue(re > 0);
    }

    @Test
    public void update() {
        GoodInfo build = GoodInfo.builder().id("1").goodImage("image").goodName("哈哈哈1").build();
        int re = service.updateById(build);
        assertTrue(re>0);
    }

}