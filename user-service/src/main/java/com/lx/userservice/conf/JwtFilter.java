package com.lx.userservice.conf;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//需要认证的url经过该过滤器
public class JwtFilter extends AccessControlFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Content-Type","application/json;charset=UTF-8");
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpResponse.setHeader("Access-Control-Allow-Methods", httpRequest.getMethod());
            httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
            return true;
        }
        return super.preHandle(request, response);
    }

    /*
     * 1. 返回true，shiro就直接允许访问url
     * 2. 返回false，shiro才会根据onAccessDenied的方法的返回值决定是否允许访问url
     * */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        String jwt = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if(jwt != null){
            JwtToken jwtToken = new JwtToken(jwt);
            //这里getSubject方法实际上就是获得一个subject
            //与原生shiro不同的地方在于没有对username和password进行封装
            //直接使用jwt进行认真，login方法实际上就是交给Realm进行认证
            try{
                getSubject(servletRequest,servletResponse).login(jwtToken);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        //直接设置401 未认证
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
