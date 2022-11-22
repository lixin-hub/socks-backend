package com.lx.userservice.service.permission;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        Map<String, Permission> all = list.stream().collect(Collectors.toMap(Permission::getId, a -> a, (k1, k2) -> k1));
        for (Permission record : list) {
            String parent = record.getParent();
            if (all.containsKey(parent)) {
                all.get(parent).addChild(record);
            }
        }
        List<Permission> root = new ArrayList<>((int) page1.getSize());
        page1.getRecords().forEach(i -> root.add(all.get(i.getId())));
        page1.setRecords(root);
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
