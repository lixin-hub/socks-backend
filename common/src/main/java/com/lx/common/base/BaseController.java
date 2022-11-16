package com.lx.common.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.lx.common.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;



@Slf4j
public class BaseController<T extends Entity<T>, D extends BaseMapper<T>> {

    @Autowired
    BaseService<T,D> service;

    @PostMapping("page")
    public Object selectPage(@RequestBody T entity) {
        Util.newIfNull(entity);
        IPage<T> page = service.selectPage(entity);
        return new Result.ResultBuilder<T>().ok().page(page).build();
    }

    @PostMapping("list")
    public Object selectList(T entity) {
        Util.newIfNull(entity);
        if (entity.getPage()==null){
            entity.setPage(new PageDto<>(1,100,false));
        }
        IPage<T> list = service.selectPage(entity);
        return Result.builder().data(list.getRecords()).build();
    }

    @GetMapping("{id}")
    public Object selectById(Serializable id) {
        T data = service.selectById(id);
        return Result.builder().ok().data(data).build();
    }

    @PostMapping("first")
    public Object selectOne(T entity) {
        Util.newIfNull(entity);
        T data = service.selectOne(entity);
        return Result.builder().ok().data(data).build();
    }

    @PostMapping("listId")
    public Object selectBatchIds(Collection<? extends Serializable> idList) {
        List<T> ts = service.selectBatchIds(idList);
        return Result.builder().ok().data(ts).build();
    }

    @PostMapping("count")
    public Object selectCount(T entity) {
        Util.newIfNull(entity);
        Integer integer = service.selectCount(entity);
        return Result.builder().status(integer > 0).data(integer).build();
    }

    @PostMapping("insert")

    public Object insert(T entity) {
        Util.newIfNull(entity);
        int insert = service.insert(entity);
        return Result.builder().status(insert > 0).data(insert).build();
    }

    @PostMapping("delete")
    public Object delete(T entity) {
        Util.newIfNull(entity);
        int delete = service.delete(entity);
        return Result.builder().status(delete > 0).data(delete).build();
    }

    @GetMapping("delete/{id}")
    public Object deleteById(@PathVariable Serializable id) {
        int i = service.deleteById(id);
        return Result.builder().status(i > 0).data(i).build();
    }

    @PostMapping("deleteBatchIds")
    public Object deleteBatchIds(Collection<? extends Serializable> idList) {
        int i = service.deleteBatchIds(idList);
        return Result.builder().status(i > 0).data(i).build();
    }

    @PostMapping("updateById")
    public Object updateById(T entity) {
        Util.newIfNull(entity);
        QueryWrapper<T> updateWrapper = entity.likeWrapper();
        updateWrapper.eq("id", entity.getId());
        entity.setNormalWrapper(updateWrapper);
        int i = service.updateById(entity);
        return Result.builder().status(i > 0).data(i).build();
    }

    @PostMapping("update")
    public Object update(T entity) {
        Util.newIfNull(entity);
        int i = service.update(entity,entity.eqWrapper());
        return Result.builder().status(i > 0).data(i).build();
    }

}
