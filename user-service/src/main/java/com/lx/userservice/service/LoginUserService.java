package com.lx.userservice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.common.util.Util;
import com.lx.userservice.dao.LoginUserDao;
import com.lx.userservice.pojo.LoginUser;
import com.lx.userservice.pojo.Menu;
import com.lx.userservice.pojo.permission.RRolePermission;
import com.lx.userservice.pojo.permission.RUserRole;
import com.lx.userservice.service.permission.PermissionService;
import com.lx.userservice.service.permission.RRolePermissionService;
import com.lx.userservice.service.permission.RUserRoleService;
import com.lx.userservice.service.permission.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoginUserService extends ServiceImpl<LoginUserDao, LoginUser> {

    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RRolePermissionService rRolePermissionService;
    @Autowired
    RUserRoleService rUserRoleService;

    @Autowired
    @Lazy
    UserInfoService userInfoService;

    public Set<Menu> getMenus(String userId) {
        LoginUser byId = getById(userId);
        Objects.requireNonNull(byId);
        Set<String> roleIds = rUserRoleService.lambdaQuery()
                .eq(RUserRole::getUserId, userId).list()
                .stream().map(RUserRole::getRoleCode)
                .collect(Collectors.toSet());
        if (roleIds.size() == 0) return null;
        try {
            Set<String> permissionIds = rRolePermissionService.lambdaQuery()
                    .in(RRolePermission::getRoleCode, roleIds).list()
                    .stream().map(RRolePermission::getPermissionCode)
                    .collect(Collectors.toSet());
            if (permissionIds.size() == 0) return null;

            Set<Menu> permissions = permissionService.listByIds(permissionIds).stream()
                    .map(permission -> Menu.builder()
                            .parent(permission.getParent())
                            .name(permission.getDescription())
                            .path(permission.getPath())
                            .id(permission.getId())
                            .build())
                    .collect(Collectors.toSet());
            if (permissions.size() == 0) return null;
            Set<Menu> collect = permissions.stream().filter(Menu::isRoot).collect(Collectors.toSet());
//            permissions.forEach(System.out::println);
            return new HashSet<>(Util.toTree(permissions, collect));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("抱歉，未知错误！", e.getCause());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        userInfoService.removeById(id);
        return super.removeById(id);
    }
}
