package com.lx.userservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.lx.common.base.BaseTreeService;
import com.lx.userservice.dao.AreaDao;
import com.lx.userservice.pojo.Area;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService extends BaseTreeService<Area, AreaDao> {
    @Override
    public List<Area> getTree(String parentId, PageDto<Area> page) {
//        if (parentId.equals("0")) return null;//不允许查询
        return super.getTree(parentId, page);
    }

    public List<Area> selectProvince() {
        return dao.selectList(new QueryWrapper<Area>().eq("parent", "0"));
    }

    public int insertBatchSomeColumn(List<Area> areas){
        return dao.insertBatchSomeColumn(areas);
    }
}
