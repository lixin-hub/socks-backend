package com.lx.userservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.lx.common.base.BaseControllerImpl;
import com.lx.common.base.Result;
import com.lx.userservice.dao.permission.PermissionDao;
import com.lx.userservice.dao.permission.RoleDao;
import com.lx.userservice.pojo.permission.Permission;
import com.lx.userservice.pojo.permission.Role;
import com.lx.userservice.service.permission.PermissionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;

@RestController
@RequestMapping("sys/perm")
public class PermissionController extends BaseControllerImpl<PermissionDao, Permission> {
    @PostMapping("page")
    @Override
    public Object selectPage(@RequestBody Permission entity) {
        return super.selectPage(entity);
    }
    @PostMapping("tree")
    public Object selectTree(@RequestBody Permission entity) {
        PageDto<Permission> tree = ((PermissionService) service).tree(entity.getPage(), entity.normalWrapper());
        return Result.builder(Permission.class).ok().page(tree).build();
    }
    @GetMapping("{roleId}/tree")
    public Object selectTree(@PathVariable String roleId) {
        log.info("roleId:{}",roleId);
        Collection<Permission> permissions = ((PermissionService) service).rolePermtTree(roleId);
        return Result.builder().ok().data(permissions).build();
    }


    @Override
    @GetMapping("selectById/{id}")
    public Object selectById(@PathVariable Serializable id) {
        return super.selectById(id);
    }

    @Override
    @PostMapping("updateById")
    public Object updateById(@RequestBody Permission entity) {
        return super.updateById(entity);
    }

    @Override
    @GetMapping("delete/{id}")
    public Object deleteById(@PathVariable Serializable id) {
        return super.deleteById(id);
    }

    @Override
    @PostMapping("insert")
    public Object insert(@RequestBody Permission entity) {
        if (entity!=null&& StringUtils.isBlank(entity.getParent())){
            entity.setParent("0");
        }
        return super.insert(entity);
    }
}
