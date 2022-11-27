package com.lx.userservice.conf;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lx.common.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * 自定义证书匹配器
 */
@Slf4j
public class JwtCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authToken, AuthenticationInfo info) {

        String token = authToken.getPrincipal().toString();
        try {
            JwtUtils.isVerify(token);
            return true;
        } catch (JWTVerificationException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
