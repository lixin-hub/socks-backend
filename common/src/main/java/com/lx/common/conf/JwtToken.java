package com.lx.common.conf;

import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.stereotype.Component;

/*
    JwtToken代替原生的UsernamePasswordToken
 */
@Component
public class JwtToken implements AuthenticationToken {

    private String jwt;

    public JwtToken(String jwt) {
        this.jwt = jwt;
    }

    JwtToken() {

    }


    //返回原来的字符串，解析交给JwtUtils实现
    @Override
    public Object getPrincipal() {
        return jwt;
    }

    //返回原来的字符串，解析交给JwtUtils实现
    @Override
    public Object getCredentials() {
        return jwt;
    }
}