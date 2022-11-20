package com.lx.userservice.pojo.permission;

import com.lx.userservice.pojo.UserInfo;
import com.lx.userservice.service.LoginUserService;
import com.lx.userservice.service.UserInfoService;
import com.lx.userservice.service.permission.PermissionService;
import com.lx.userservice.service.permission.RRolePermissionService;
import com.lx.userservice.service.permission.RUserRoleService;
import com.lx.userservice.service.permission.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RUserRoleService rUserRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RRolePermissionService rRolePermissionService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        // 查询用户，获取角色ids
        UserInfo user = userInfoService.lambdaQuery().eq(UserInfo::getUsername, username).one();
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
                .map(item -> Long.toString(item.getPermissionCode()))
                .collect(Collectors.toSet());
        // 获取权限名称
        List<Permission> permissions = permissionService.listByIds(permIds);
        Set<String> permNames = permissions.stream().map(Permission::getPermission).collect(Collectors.toSet());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        permNames.forEach(log::info);
        authorizationInfo.addRoles(roleNames);
        authorizationInfo.addStringPermissions(permNames);
        return authorizationInfo;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        LoginUser account = loginUserService.selectOne(LoginUser.builder().username(token.getUsername()).build());
//        log.info("account:{}", account);
//        if (account != null) {
//            return new SimpleAuthenticationInfo(account, account.getPassword(), getName());
//        }
        return null;
    }
}