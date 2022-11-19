package com.lx.userservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.common.base.BaseController;
import com.lx.common.base.Result;
import com.lx.userservice.dao.AreaDao;
import com.lx.userservice.pojo.Area;
import com.lx.userservice.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("addr")
public class AreaController extends BaseController<Area, AreaDao> {
    @Autowired
    AreaService service;

    @GetMapping("parent/{parentId}")
    public Object select(@PathVariable String parentId) {
        if (parentId==null||parentId.isEmpty())
            parentId = "0";
        List<Area> level = service.selectList(new QueryWrapper<Area>().eq("parent", parentId));
        return Result.builder().ok().data(level).build();
    }
}
