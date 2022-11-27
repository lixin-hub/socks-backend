package com.lx.userservice.conf;

import org.apache.shiro.authc.AuthenticationToken;

/*
    JwtToken代替原生的UsernamePasswordToken
 */
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