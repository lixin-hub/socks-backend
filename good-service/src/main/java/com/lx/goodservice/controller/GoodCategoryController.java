package com.lx.goodservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.lx.common.base.BaseController;
import com.lx.common.base.Result;
import com.lx.goodservice.dao.GoodCategoryDao;
import com.lx.goodservice.pojo.GoodCategory;
import com.lx.goodservice.service.GoodCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author LIXIN
 * @description 商品相关api
 * @date 2022/11/11 21:11
 */
@RestController
@RequestMapping("good/category")
@Slf4j
public class GoodCategoryController extends BaseController<GoodCategory, GoodCategoryDao> {

    @Autowired
    GoodCategoryService service;


    @PostMapping("page")
    @Override
    public Object selectPage(@RequestBody GoodCategory entity) {
        PageDto<GoodCategory> page = entity.getPage();
        List<GoodCategory> tree = service.getTree(entity.getParent(), page);
        page.setRecords(tree);
        return Result.builder(GoodCategory.class).ok().page(page).build();
    }

    @PostMapping("list")
    public Object selectList(@RequestBody GoodCategory entity) {
        return super.selectList(entity);
    }

    @GetMapping("tree/{parentId}")
    public Object selectTree(@PathVariable String parentId) {
        List<GoodCategory> tree = service.getTree(parentId,null);
        return Result.builder().ok().data(tree).build();
    }

    @GetMapping("{id}")
    public Object selectById(@PathVariable Serializable id) {
        return super.selectById(id);
    }


    @PostMapping("insert")
    public Object insert(@RequestBody GoodCategory entity) {
        return super.insert(entity);
    }


    @GetMapping("delete/{id}")
    public Object deleteById(@PathVariable Serializable id) {
        return super.deleteById(id);
    }


    @PostMapping("updateById")
    public Object updateById(@RequestBody GoodCategory entity) {
        return super.updateById(entity);
    }


}
