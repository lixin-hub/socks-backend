package com.lx.common.conf;

import com.alibaba.fastjson.JSONArray;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lx.common.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtRealm extends AuthorizingRealm {


    /*
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
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

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String jwt = (String) authenticationToken.getPrincipal();
        JwtUtils.isVerify(jwt);
        return new SimpleAuthenticationInfo(jwt, jwt, "JwtRealm");
    }
}