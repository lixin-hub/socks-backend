package com.lx.goodservice.controller;

import com.lx.common.base.BaseController;
import com.lx.common.base.Result;
import com.lx.goodservice.dao.GoodInfoDao;
import com.lx.goodservice.dto.AddGoodDTO;
import com.lx.goodservice.pojo.GoodInfo;
import com.lx.goodservice.service.GoodInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author LIXIN
 * @description 商品相关api
 * @date 2022/11/11 21:11
 */
@RestController
@RequestMapping("good")
@Slf4j
public class GoodInfoController extends BaseController<GoodInfo, GoodInfoDao> {


    @PostMapping("page")
    @Override
    public Object selectPage(@RequestBody GoodInfo entity) {
//        entity.queryType("like");
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
    public Object selectBatchIds(@RequestBody List<Serializable> idList) {
        if (idList==null||idList.size()==0){
            return Result.builder().notOk(400,"参数为空").build();
        }
        return super.selectBatchIds(idList);
    }

    @PostMapping("count")
    public Object selectCount(@RequestBody GoodInfo entity) {
        return super.selectCount(entity);
    }

    @PostMapping("addGoodInfo")
    @RequiresPermissions("good:add")
    public Object addGoodInfo(@RequestBody AddGoodDTO entity) {
        if (entity == null) {
            return Result.builder().notOk(400).message("参数为空~").build();
        }
        if (StringUtils.isBlank(entity.getGoodName()) ||
                entity.getGoodPrice() == 0 ||
                entity.getGoodStoke() == 0
        ) {
            return Result.builder().notOk(400).message("参数错误").data(entity).build();
        }

        int i = ((GoodInfoService) service).addGoodInfo(entity);
        return Result.builder().status(i > 0).build();
    }

    @PostMapping("delete")
    @RequiresPermissions("goods:delete")
    @Override
    public Object delete(@RequestBody GoodInfo entity) {
        return super.delete(entity);
    }

    @GetMapping("delete/{id}")
    @RequiresPermissions("goods:delete")
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
