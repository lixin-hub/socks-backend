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
    public void list() {
        List<GoodInfo> goodInfos = service.selectList(GoodInfo.builder().goodName(".").build());
        assertNotNull(goodInfos);
        goodInfos.forEach(System.out::println);
    }

    @Test
    public void page() {
        GoodInfo nike = GoodInfo.builder().goodName("nike")
                .build();
        nike.setPage(new PageDto<>(1, 3));
        IPage<GoodInfo> page = service.selectPage(nike
                , new QueryWrapper<GoodInfo>().like("good_name", "ni")
        );
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