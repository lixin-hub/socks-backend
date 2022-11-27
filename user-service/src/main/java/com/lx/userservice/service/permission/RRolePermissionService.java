package com.lx.userservice.service.permission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.common.util.Util;
import com.lx.userservice.dao.permission.RRolePermissionDao;
import com.lx.userservice.pojo.UserInfo;
import com.lx.userservice.pojo.permission.Permission;
import com.lx.userservice.pojo.permission.RRolePermission;
import com.lx.userservice.pojo.permission.RUserRole;
import com.lx.userservice.pojo.permission.Role;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class RRolePermissionService extends ServiceImpl<RRolePermissionDao, RRolePermission> {

    @Autowired
    @Lazy
    RoleService roleService;
    @Autowired
    @Lazy
    PermissionService permissionService;

    @Transactional(rollbackFor = Exception.class)
    public boolean mallocPermission(String roleId, String pids) {
        List<RRolePermission> rRolePermissions;
        Role one = roleService.lambdaQuery().eq(Role::getId, roleId).one();
        Objects.requireNonNull(one);
        if (StringUtils.isBlank(pids)) {
            throw new IllegalArgumentException("权限列表为空");
        }
        List<String> strings = Arrays.asList(pids.split(","));
        rRolePermissions = permissionService.listByIds(strings).stream()
                .map(a -> RRolePermission.builder().permissionCode(a.getId()).roleCode(one.getId()).build())
                .collect(Collectors.toList());
        Objects.requireNonNull(rRolePermissions);
        if (rRolePermissions.size() == 0) {
            throw new IllegalArgumentException("找不到权限列表：" + pids);
        }
        rRolePermissions.stream().map(RRolePermission::toString).forEach(log::debug);
        boolean remove = remove(new QueryWrapper<RRolePermission>().eq("role_code", roleId));
        if (!remove) {//当没有数据的时候也会显示更新失败
            log.debug("更新失败?");
            boolean b = lambdaQuery().eq(RRolePermission::getRoleCode, roleId).list().size() == 0;
            if (!b)
                throw new RuntimeException("更新失败");
        }
        ;
        return saveBatch(rRolePermissions);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Permission> removePermission(String roleId, String pid) {
        if (StringUtils.isBlank(pid)) throw new IllegalArgumentException("权限列表为空");
        boolean b = lambdaUpdate().eq(RRolePermission::getRoleCode, roleId).eq(RRolePermission::getPermissionCode, pid).remove();
        if (!b) throw new RuntimeException("移除失败！");
        Role byId = roleService.getById(roleId);
        Objects.requireNonNull(byId);
        Set<String> permids = super.lambdaQuery().eq(RRolePermission::getRoleCode, roleId).list().stream()
                .map(RRolePermission::getPermissionCode)
                .collect(Collectors.toSet());
        List<Permission> permissions = permissionService.listByIds(permids);
        return Util.toTree(permissions, permissions.stream()
                .filter(i -> "0".equals(i.getParent()))
                .collect(Collectors.toSet()));
    }

}
