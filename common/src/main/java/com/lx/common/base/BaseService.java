package com.lx.common.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Slf4j
public abstract class BaseService<T extends Entity<T>, D extends BaseMapper<T>> {

    @Autowired
    public D dao;

    public IPage<T> selectPage(T t) {
        return selectPage(t, t.normalWrapper());
    }

    public IPage<T> selectPage(T t, QueryWrapper<T> wrapper) {
        Page<T> page = t.getPage();
        if (page == null) {
            page = new Page<>();
        }
        page.setSearchCount(true);
//        page.setOptimizeCountSql(false);
        return dao.selectPage(page, wrapper);
    }

    public List<T> selectList(T t) {
        return selectList(t.normalWrapper());
    }

    public List<T> selectList(QueryWrapper<T> wrapper) {
        return dao.selectList(wrapper);
    }

    public T selectById(Serializable id) {
        return dao.selectById(id);
    }

    public T selectOne(T t) {
        return selectOne(t, t.normalWrapper());
    }

    public T selectOne(T t, QueryWrapper<T> wrapper) {
        return dao.selectOne(wrapper);
    }

    public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
        return dao.selectBatchIds(idList);
    }

    public Integer selectCount(T t) {
        return selectCount(t, t.normalWrapper());
    }

    public Integer selectCount(T t, QueryWrapper<T> wrapper) {
        return dao.selectCount(wrapper);
    }

    public int insert(T t) {
        return dao.insert(t);
    }

    public int delete(T t) {
        return delete(t.normalWrapper());
    }

    public int delete(QueryWrapper<T> wrapper) {
        return dao.delete(wrapper);
    }

    public int deleteById(Serializable id) {
        return dao.deleteById(id);
    }

    public int deleteBatchIds(@Param("coll") Collection<? extends Serializable> idList) {
        return dao.deleteBatchIds(idList);
    }

    public int updateById(T entity) {
        return dao.updateById(entity);
    }

    public int update(T entity, Wrapper<T> updateWrapper) {
        return dao.update(entity, updateWrapper);
    }

    public <S> S restEntityData(String res, Class<S> tClass) {
        return (S) restResult(res, "data", tClass);
    }

    public <S> List<S> restListData(String res, Class<S> tClass) {
        return (List<S>) restResult(res, "list", tClass);
    }

    public <S> IPage<S> restPage(String res, Class<S> tClass) {
        return (IPage<S>) restResult(res, "page", tClass);
    }

    private <S> Object restResult(String jsonResult, String type, Class<S> clazz)  {
        JSONObject result = JSONObject.parseObject(jsonResult);
        Boolean status = result.getBoolean("status");
        String message = result.getString("message");
        if (!status) throw new IllegalStateException(message);
        switch (type) {
            case "entity": {
                JSONObject data = result.getJSONObject("data");
                return data.toJavaObject(clazz);
            }
            case "list": {
                JSONArray data = result.getJSONArray("data");
                return data.toJavaList(clazz);
            }
            case "page":
                JSONObject page = result.getJSONObject("page");
                return page.toJavaObject(Page.class);
        }
        return null;
    }

}
