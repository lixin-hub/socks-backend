package com.lx.userservice.service;

import com.lx.userservice.pojo.LoginUser;
import com.lx.userservice.pojo.Menu;
import com.lx.userservice.pojo.UserInfo;
import com.lx.userservice.pojo.permission.Permission;
import com.lx.userservice.pojo.permission.RRolePermission;
import com.lx.userservice.pojo.permission.RUserRole;
import com.lx.userservice.pojo.permission.Role;
import com.lx.userservice.service.permission.PermissionService;
import com.lx.userservice.service.permission.RRolePermissionService;
import com.lx.userservice.service.permission.RUserRoleService;
import com.lx.userservice.service.permission.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginUserServiceTest {
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private UserInfoService userInfoService;
    @Resource
    private RUserRoleService rUserRoleService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RRolePermissionService rRolePermissionService;
    @Test
    public void testSelect(){
        String username ="lixin";
        // 查询用户，获取角色ids
        LoginUser user = loginUserService.lambdaQuery().eq(LoginUser::getUsername, username).one();
        //用户的角色id列表
        Set<String> roleIds = rUserRoleService.lambdaQuery().eq(RUserRole::getUserId, user.getId())
                .list()
                .stream()
                .map(RUserRole::getId)
                .collect(Collectors.toSet());

        // 查询角色，获取角色名、权限ids
        List<Role> roles = roleService.listByIds(roleIds);
        //角色名称
        Set<String> roleNames = roles.stream().map(Role::getRoleName).collect(Collectors.toSet());
        roleNames.forEach(log::info);
        //权限id列表
        Set<String> permIds = rRolePermissionService.lambdaQuery().in(RRolePermission::getRoleCode, roleIds)
                .list()
                .stream()
                .map(RRolePermission::getPermissionCode)
                .collect(Collectors.toSet());
        // 获取权限名称
        List<Permission> permissions = permissionService.listByIds(permIds);
        Set<String> permNames = permissions.stream().map(Permission::getPermission).collect(Collectors.toSet());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        permNames.forEach(log::info);
    }

    @Test
    public void getMenus() {
        Set<Menu> menus = loginUserService.getMenus("1");
        log.info(String.valueOf(menus));
    }
}