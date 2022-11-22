package com.lx.userservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.lx.common.base.BaseControllerImpl;
import com.lx.common.base.Result;
import com.lx.common.util.Util;
import com.lx.userservice.dao.permission.RoleDao;
import com.lx.userservice.pojo.permission.Permission;
import com.lx.userservice.pojo.permission.Role;
import com.lx.userservice.service.permission.RRolePermissionService;
import com.lx.userservice.service.permission.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("sys/role")
public class RoleController extends BaseControllerImpl<RoleDao, Role> {
    @Autowired
    RRolePermissionService rRolePermissionService;

    @PostMapping("page")
    @Override
    public Object selectPage(@RequestBody Role entity) {
        return super.selectPage(entity);
    }

    @PostMapping("tree")
    public Object selectTree(@RequestBody Role entity) {
        Util.newIfNull(entity);
        PageDto<Role> tree = ((RoleService) service).tree(entity);
        return Result.builder(Role.class).ok().page(tree).build();
    }



    @Override
    @GetMapping("selectById/{id}")
    public Object selectById(@PathVariable Serializable id) {
        return super.selectById(id);
    }

    @Override
    @PostMapping("updateById")
    public Object updateById(@RequestBody Role entity) {
        return super.updateById(entity);
    }

    @Override
    @GetMapping("delete/{id}")
    public Object deleteById(@PathVariable Serializable id) {
        return super.deleteById(id);
    }

    @Override
    @PostMapping("insert")
    public Object insert(@RequestBody Role entity) {
        return super.insert(entity);
    }

    /**
     * @param roleId 角色id
     * @param pids 权限列表
     * @return 分配权限
     */
    @GetMapping("{roleId}/malloc/{pids}")
    public Object mallocPermission(@PathVariable String roleId, @PathVariable String pids) {
        boolean isOk = rRolePermissionService.mallocPermission(roleId, pids);
        return Result.builder().status(isOk).build();
    }

    /**
     *
     * @param roleId
     * @return 取消分配权限
     */
    @GetMapping("{roleId}/remove/{pid}")
    public Object cancelPermission(@PathVariable String roleId, @PathVariable String pid) {
        List<Permission> permissions = rRolePermissionService.removePermission(roleId, pid);
        return Result.builder().data(permissions).ok().build();
    }
}
