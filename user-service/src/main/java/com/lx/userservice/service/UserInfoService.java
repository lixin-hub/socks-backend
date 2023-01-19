package com.lx.userservice.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.userservice.dao.UserInfoDao;
import com.lx.userservice.pojo.LoginUser;
import com.lx.userservice.pojo.UserInfo;
import com.lx.userservice.pojo.permission.Permission;
import com.lx.userservice.pojo.permission.RRolePermission;
import com.lx.userservice.pojo.permission.RUserRole;
import com.lx.userservice.pojo.permission.Role;
import com.lx.userservice.service.permission.PermissionService;
import com.lx.userservice.service.permission.RRolePermissionService;
import com.lx.userservice.service.permission.RUserRoleService;
import com.lx.userservice.service.permission.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserInfoService extends ServiceImpl<UserInfoDao, UserInfo> {

    @Autowired
    LoginUserService loginUserService;
    @Autowired
    RoleService roleService;
    @Autowired
    private RUserRoleService rUserRoleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RRolePermissionService rRolePermissionService;

    @Override
    public <E extends IPage<UserInfo>> E page(E page, Wrapper<UserInfo> queryWrapper) {
        E pages = super.page(page, queryWrapper);
        List<UserInfo> records = pages.getRecords();
        Set<String> userIds = records.stream().map(UserInfo::getId)
                .collect(Collectors.toSet());
        log.debug(userIds.toString());
        List<LoginUser> loginUsers = loginUserService.listByIds(userIds);
        Map<String, LoginUser> map = loginUsers.stream()
                .collect(Collectors.toMap(LoginUser::getId, a -> a, (k1, k2) -> k1));
        Map<String, List<String>> idRole = new HashMap<>();
        userIds.forEach(id -> {
            Set<String> roleCodes = rUserRoleService.lambdaQuery().eq(RUserRole::getUserId, id)
                    .list()
                    .stream()
                    .map(RUserRole::getRoleCode)
                    .collect(Collectors.toSet());
            if (roleCodes.size() > 0)
                idRole.put(id, roleService.listByIds(roleCodes).stream().map(Role::getRoleName).collect(Collectors.toList()));
        });

        List<UserInfo> userInfoList = records.stream()
                .peek(a -> {
                    a.setUsername(map.get(a.getId()).getUsername());
                    if (idRole.containsKey(a.getId())) {
                        a.setRoles(idRole.get(a.getId()));
                    } else {
                        a.setRoles(new ArrayList<>());
                    }
                })
                .collect(Collectors.toList());
        pages.setRecords(userInfoList);
        return pages;
    }

    @Override
    public UserInfo getById(Serializable id) {
        UserInfo userInfo = super.getById(id);
        List<Role> roles = rUserRoleService.getRolesByUserId(userInfo);
        userInfo.setRoles(roles.stream().map(Role::getRoleName).collect(Collectors.toList()));
        return userInfo;
    }

    public HashMap<String, Set<String>> getRoleAndPermission(LoginUser user) {
        HashMap<String, Set<String>> res = new HashMap<>();
        Set<String> roleNames;
        Set<String> permIds = null;
        List<Permission> permissions;
        //用户的角色id列表
        Set<String> roleIds = rUserRoleService.lambdaQuery().eq(RUserRole::getUserId, user.getId())
                .list()
                .stream()
                .map(RUserRole::getRoleCode)
                .collect(Collectors.toSet());
        if (roleIds.size() != 0) {
            // 查询角色，获取角色名、权限ids
            List<Role> roles = roleService.listByIds(roleIds);
            //角色名称
            roleNames = roles.stream().map(Role::getRoleName).collect(Collectors.toSet());
            res.put("roleNames", roleNames);
            permIds = rRolePermissionService.lambdaQuery()
                    .in(RRolePermission::getRoleCode, roleIds)
                    .list()
                    .stream()
                    .map(RRolePermission::getPermissionCode)
                    .collect(Collectors.toSet());
        }
        if (permIds != null && permIds.size() != 0) {
            permissions = permissionService.listByIds(permIds);
            Set<String> permNames = permissions.stream().map(Permission::getPermission).collect(Collectors.toSet());
            res.put("permNames", permNames);
            return res;
        }
        res.put("permNames", new HashSet<>());
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(UserInfo entity) {
        LoginUser loginUser = LoginUser.builder().password(entity.getPassword()).username(entity.getUsername()).build();
        loginUserService.save(loginUser);
        //分配默认权限为user
        Role userRole = roleService.lambdaQuery().eq(Role::getRoleName, "user").one();
        rUserRoleService.save(RUserRole.builder().userId(loginUser.getId()).roleCode(userRole.getId()).build());
        entity.setId(loginUser.getId());
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        loginUserService.removeById(id);
        return super.removeById(id);
    }
    public boolean hasPhone(String phone){
        return super.lambdaQuery().eq(UserInfo::getPhone,phone).one()!=null;
    }
}
