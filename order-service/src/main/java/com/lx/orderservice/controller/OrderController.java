package com.lx.orderservice.controller;

import com.lx.common.base.BaseController;
import com.lx.common.base.Result;
import com.lx.common.pojo.orderservice.GoodInfo;
import com.lx.orderservice.dao.OrderDao;
import com.lx.orderservice.pojo.Order;
import com.lx.orderservice.pojo.ROrderGood;
import com.lx.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController extends BaseController<Order, OrderDao> {

    @PostMapping("page")
    public Object selectPage(@RequestBody Order entity) {
        return super.selectPage(entity);
    }

    @PostMapping("insert")
    public Object insert(@RequestBody Order entity) {
        return super.insert(entity);
    }

    @GetMapping("goods/{id}")
    public Object selectOrderGood(@PathVariable Serializable id) {
        if (id==null) return Result.builder().notOk(400,"id为空");
        List<ROrderGood> goodInfos = null;
        try {
            goodInfos = ((OrderService) service).selectOrderGood((String) id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (goodInfos==null)
            return Result.builder().notOk(401,"找不到关联商品").build();
        return Result.builder().ok().data(goodInfos).build();
    }
    @GetMapping("selectById/{id}")
    public Object selectById(@PathVariable Serializable id) {
        return super.selectById(id);
    }

    @GetMapping("deleteById/{id}")
    public Object deleteById(@PathVariable Serializable id) {
        return super.deleteById(id);
    }

    @PostMapping("update")
    public Object update(@RequestBody Order entity) {
        return super.updateById(entity);
    }


}
