package com.lx.goodservice.controller;

import com.lx.common.base.BaseController;
import com.lx.common.base.Result;
import com.lx.goodservice.dao.GoodAttributeDao;
import com.lx.goodservice.pojo.GoodAttribute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author LIXIN
 * @description 商品相关api
 * @date 2022/11/11 21:11
 */
@RestController
@RequestMapping("good/attribute")
@Slf4j
public class GoodAttributeController extends BaseController<GoodAttribute, GoodAttributeDao> {


    @PostMapping("page")
    public Object selectPage(@RequestBody GoodAttribute entity) {
        return super.selectPage(entity);
    }

    @PostMapping("insert")
    public Object insert(@RequestBody GoodAttribute entity) {
        return super.insert(entity);
    }

    @PostMapping("list")
    public Object selectList(@RequestBody GoodAttribute entity) {
        return super.selectList(entity);
    }
    @GetMapping("{attrType}/{catId}/list")
    public Object selectList(@PathVariable String catId,@PathVariable String attrType) {
        if (StringUtils.isBlank(catId)){
            return Result.builder().notOk(400,"找不到分类id").build();
        } if (StringUtils.isBlank(attrType)){
            return Result.builder().notOk(400,"找不到属性类型").build();
        }
        GoodAttribute eq = GoodAttribute.builder().catId(catId).attrType(attrType).build().queryType("eq");
        return super.selectList(eq);
    }

    @GetMapping("/{id}")
    public Object selectById(@PathVariable Serializable id) {
        return super.selectById(id);
    }


    @PostMapping("listId")
    public Object selectBatchIds(Collection<? extends Serializable> idList) {
        return super.selectBatchIds(idList);
    }

    @PostMapping("count")
    public Object selectCount(@RequestBody GoodAttribute entity) {
        return super.selectCount(entity);
    }


    @PostMapping("delete")
    @Override
    public Object delete(@RequestBody GoodAttribute entity) {
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
    public Object updateById(@RequestBody GoodAttribute entity) {
        return super.updateById(entity);
    }

    @PostMapping("update")
    public Object update(@RequestBody GoodAttribute entity) {
        return super.update(entity);
    }

}
