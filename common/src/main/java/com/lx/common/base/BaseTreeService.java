package com.lx.common.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseTreeService<T extends TreeEntity<T>, D extends BaseMapper<T>> extends BaseService<T, D> {

    public List<T> getTree(String parentId, PageDto<T> page) {
        if (parentId == null) parentId = "0";
        List<T> selectList = selectList(new QueryWrapper<>());
        HashMap<String, T> hashMap = new HashMap<>(30);
        List<T> rootList = new ArrayList<>(20);
        for (T t : selectList) {
            String parent = t.getParent();
            hashMap.put(t.getId(), t);
            if (parent.equals(parentId)) {
                rootList.add(t);
            }
        }
        for (T t : selectList) {
            String parent = t.getParent();
            if (hashMap.containsKey(parent)) {
                hashMap.get(parent).addChild(t);
            }
        }
        if (page==null) return rootList;
        long size = page.getSize();
        long offset = page.offset();
        int end = (int) (offset + size);
        if (offset > rootList.size()) {
            return new ArrayList<>();
        }
        page.setTotal(rootList.size());
        return rootList.subList((int) offset, Math.min(end, rootList.size()));
    }

//    public List<T> getParent(){
//
//    }
}
