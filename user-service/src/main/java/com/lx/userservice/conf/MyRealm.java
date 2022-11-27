package com.lx.userservice.conf;

import com.alibaba.fastjson.JSONArray;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lx.common.util.JwtUtils;
import com.lx.userservice.pojo.LoginUser;
import com.lx.userservice.service.LoginUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private LoginUserService loginUserService;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }


    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 查询用户，获取角色ids
        String jwt = (String) principalCollection.getPrimaryPrincipal();
        DecodedJWT decodedJWT = JwtUtils.getToken(jwt);
        Map<String, Claim> claims = decodedJWT.getClaims();
        Claim permission = claims.get("permissions");
        List<String> permissions = JSONArray.parseArray(permission.asString(), String.class);
        log.info("permissions:{}", permissions);
        Claim role = claims.get("roles");
        List<String> roles = JSONArray.parseArray(role.asString(), String.class);
        log.info("roles:{}", roles);
        Claim userId = claims.get("userId");
        log.info("userId:{}", userId);
        Claim username = claims.get("userName");
        log.info("username:{}", username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermissions(permissions);
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