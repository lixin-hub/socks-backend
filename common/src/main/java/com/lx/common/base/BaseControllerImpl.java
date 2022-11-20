package com.lx.common.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class BaseControllerImpl<D extends BaseMapper<T>, T extends Entity<T>> {

    @Autowired
    private ServiceImpl<D, T> service;

    @PostMapping("page")
    public Object selectPage(@RequestBody T entity) {
        Util.newIfNull(entity);
        IPage<T> page = service.page(entity.getPage(), entity.normalWrapper);
        return new Result.ResultBuilder<T>().ok().page(page).build();
    }

    @PostMapping("list")
    public Object selectList(T entity) {
        Util.newIfNull(entity);
        if (entity.getPage() == null) {
            entity.setPage(new PageDto<>(1, 100, false));
        }
        IPage<T> list = service.page(entity.getPage(),entity.normalWrapper);
        return Result.builder().data(list.getRecords()).build();
    }

    @GetMapping("{id}")
    public Object selectById(Serializable id) {
        T data = service.getById(id);
        return Result.builder().ok().data(data).build();
    }

    @PostMapping("first")
    public Object selectOne(T entity) {
        Util.newIfNull(entity);
        T data = service.getOne(entity.normalWrapper);
        return Result.builder().ok().data(data).build();
    }

    @PostMapping("listId")
    public Object selectBatchIds(List<Serializable> idList) {
        List<T> ts = service.listByIds(idList);
        return Result.builder().ok().data(ts).build();
    }

    @PostMapping("count")
    public Object selectCount(T entity) {
        Util.newIfNull(entity);
        Integer integer = service.count(entity.normalWrapper);
        return Result.builder().status(integer > 0).data(integer).build();
    }

    @PostMapping("insert")

    public Object insert(T entity) {
        Util.newIfNull(entity);
        if (entity.getId() != null) {
            return this.updateById(entity);
        }
        boolean save = service.save(entity);
        return Result.builder().status(save).message("保存成功").build();
    }

    @PostMapping("delete")
    public Object delete(T entity) {
        Util.newIfNull(entity);
        boolean delete = service.remove(entity.normalWrapper);
        return Result.builder().status(delete).build();
    }

    @GetMapping("delete/{id}")
    public Object deleteById(@PathVariable Serializable id) {
        Util.newIfNull(id);
        boolean i = service.removeById(id);
        String message;
        if (i) message = "删除成功";
        else message = "删除失败";
        return Result.builder().status(i).message(message).data(i).build();
    }

    @PostMapping("deleteBatchIds")
    public Object deleteBatchIds(Collection<? extends Serializable> idList) {
        boolean i = service.removeByIds(idList);
        return Result.builder().status(i).build();
    }

    @PostMapping("updateById")
    public Object updateById(T entity) {
        Util.newIfNull(entity);
        QueryWrapper<T> updateWrapper = entity.likeWrapper();
        updateWrapper.eq("id", entity.getId());
        entity.setNormalWrapper(updateWrapper);
        boolean i = service.updateById(entity);
        return Result.builder().ok().status(i).build();
    }

    @PostMapping("update")
    public Object update(T entity) {
        Util.newIfNull(entity);
        boolean i = service.update(entity,entity.normalWrapper);
        return Result.builder().status(i).build();
    }

}
