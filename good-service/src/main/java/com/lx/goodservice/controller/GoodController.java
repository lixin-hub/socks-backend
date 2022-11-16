package com.lx.goodservice.controller;

import com.lx.common.base.BaseController;
import com.lx.goodservice.dao.GoodInfoDao;
import com.lx.goodservice.pojo.GoodInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author LIXIN
 * @description 商品相关api
 * @date 2022/11/11 21:11
 */
@RestController
@RequestMapping("good")
@Slf4j
public class GoodController extends BaseController<GoodInfo, GoodInfoDao> {


    @PostMapping("page")
    @Override
    public Object selectPage(@RequestBody GoodInfo entity) {
        return super.selectPage(entity);
    }

    @PostMapping("list")
    public Object selectList(@RequestBody GoodInfo entity) {
        return super.selectList(entity);
    }

    @GetMapping("{id}")
    public Object selectById(@PathVariable Serializable id) {
        return super.selectById(id);
    }

    @PostMapping("first")
    public Object selectOne(@RequestBody GoodInfo entity) {
        return super.selectOne(entity);
    }

    @PostMapping("listId")
    public Object selectBatchIds(Collection<? extends Serializable> idList) {
        return super.selectBatchIds(idList);
    }

    @PostMapping("count")
    public Object selectCount(@RequestBody GoodInfo entity) {
        return super.selectCount(entity);
    }

    @PostMapping("insert")
    public Object insert(@RequestBody GoodInfo entity) {
        return super.insert(entity);
    }

    @PostMapping("delete")
    @Override
    public Object delete(@RequestBody GoodInfo entity) {
        return super.delete(entity);
    }

    @GetMapping("delete/{id}")
    public Object deleteById(@PathVariable Serializable id) {
        return super.deleteById(id);
    }

    @PostMapping("deleteBatchIds")
    public Object deleteBatchIds(Collection<? extends Serializable> idList) {
        return super.deleteBatchIds(idList);
    }

    @PostMapping("updateById")
    public Object updateById(@RequestBody GoodInfo entity) {
        return super.updateById(entity);
    }

    @PostMapping("update")
    public Object update(@RequestBody GoodInfo entity) {
        return super.update(entity);
    }

}
