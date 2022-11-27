package com.lx.userservice.service.permission;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.common.base.TreeEntity;
import com.lx.common.util.Util;
import com.lx.userservice.dao.permission.PermissionDao;
import com.lx.userservice.pojo.permission.Permission;
import com.lx.userservice.pojo.permission.RRolePermission;
import com.lx.userservice.pojo.permission.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PermissionService extends ServiceImpl<PermissionDao, Permission> {
    @Autowired
    RRolePermissionService rRolePermissionService;
    @Autowired
    @Lazy
    PermissionService permissionService;

    @Autowired
    @Lazy
    RoleService roleService;

    public <E extends IPage<Permission>> E tree(E page, Wrapper<Permission> queryWrapper) {
        E page1 = super.page(page, queryWrapper);
        List<Permission> list = super.list();
        List<Permission> tree = Util.toTree(list, list.stream().filter(TreeEntity::isRoot).collect(Collectors.toSet()));
        page1.setRecords(tree);
        return page1;
    }

    @Override
    public boolean removeById(Serializable id) {
        rRolePermissionService.removeById(id);
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        rRolePermissionService.removeByIds(idList);
        return super.removeByIds(idList);
    }

    public Collection<Permission> rolePermtTree(String roleId) {
        Role byId = roleService.getById(roleId);
        Objects.requireNonNull(byId);
        Set<String> pids = rRolePermissionService.lambdaQuery().eq(RRolePermission::getRoleCode, byId.getId()).list().stream().map(RRolePermission::getPermissionCode).collect(Collectors.toSet());
        List<Permission> permissions = permissionService.listByIds(pids);
        return Util.toTree(permissions, permissions.stream().filter(i -> Objects.equals(i.getParent(), "0")).collect(Collectors.toSet()));
    }
}
