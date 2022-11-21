package com.lx.userservice.conf;

import com.alibaba.fastjson.JSONObject;
import com.lx.common.util.JwtUtils;
import com.lx.userservice.pojo.LoginUser;
import com.lx.userservice.pojo.permission.Permission;
import com.lx.userservice.pojo.permission.RRolePermission;
import com.lx.userservice.pojo.permission.RUserRole;
import com.lx.userservice.pojo.permission.Role;
import com.lx.userservice.service.LoginUserService;
import com.lx.userservice.service.UserInfoService;
import com.lx.userservice.service.permission.PermissionService;
import com.lx.userservice.service.permission.RRolePermissionService;
import com.lx.userservice.service.permission.RUserRoleService;
import com.lx.userservice.service.permission.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    public HashMap<String, Set<String>> getRoleAndPermission(LoginUser user) {
        HashMap<String, Set<String>> res = new HashMap<>();
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
        res.put("roleNames", roleNames);
        Set<String> permIds = rRolePermissionService.lambdaQuery().in(RRolePermission::getRoleCode, roleIds)
                .list()
                .stream()
                .map(item -> Long.toString(item.getPermissionCode()))
                .collect(Collectors.toSet());
        // 获取权限名称
        List<Permission> permissions = permissionService.listByIds(permIds);
        Set<String> permNames = permissions.stream().map(Permission::getPermission).collect(Collectors.toSet());
        res.put("permNames", permNames);
        return res;
    }

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
        LoginUser user = loginUserService.lambdaQuery().eq(LoginUser::getUsername, username).one();
        HashMap<String, Set<String>> roleAndPermission = getRoleAndPermission(user);
        Set<String> roleNames = roleAndPermission.get("roleNames");
        Set<String> permNames = roleAndPermission.get("permNames");
        roleNames.forEach(log::info);
        //权限id列表
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        permNames.forEach(log::info);
        HashMap<String, String> jwt = new HashMap<>();
        jwt.put("permNames", JSONObject.toJSONString(permNames));
        jwt.put("roleNames", JSONObject.toJSONString(roleNames));
        jwt.put("userId", user.getId());
        jwt.put("userName", user.getUsername());
        String token = JwtUtils.getToken(jwt);
        log.info("token:{}", token);
        SecurityUtils.getSubject().getSession().setAttribute("token", token);
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
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        LoginUser account = loginUserService.lambdaQuery().eq(LoginUser::getUsername, token.getUsername()).one();
        log.info("LoginUser:{}", account);
        if (account != null) {
            return new SimpleAuthenticationInfo(account, account.getPassword(), getName());
        }
        return null;
    }
}