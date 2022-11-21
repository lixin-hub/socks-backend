package com.lx.userservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.lx.common.base.BaseControllerImpl;
import com.lx.common.base.Result;
import com.lx.common.util.JwtUtils;
import com.lx.userservice.conf.MyRealm;
import com.lx.userservice.dao.LoginUserDao;
import com.lx.userservice.pojo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Set;

@Slf4j
@RequestMapping("auth")
@RestController
public class LoginUserController extends BaseControllerImpl<LoginUserDao, LoginUser> {

    @Autowired
    MyRealm myRealm;
    @PostMapping("register")
    public Object register(@RequestBody LoginUser loginUser) {
        // 密码加密
        String pass = EncryptUtils.md5Base64(loginUser.getPassword());
        loginUser.setPassword(pass);
//        return super.insert(loginUser);
        return null;
    }

    @PostMapping("login")
    public Object login(@RequestBody LoginUser loginUser) {
        log.info(loginUser.toString());
        // 1.创建UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginUser.getUsername(), loginUser.getPassword());
        // 使用记住我功能
        usernamePasswordToken.setRememberMe(loginUser.isRememberMe());
        // 2.创建Subject 用户主体
        Subject subject = SecurityUtils.getSubject();
        String message = "";
        try {
            // 3.前期准备后，开始登录
            subject.login(usernamePasswordToken);
        } catch (IncorrectCredentialsException e) {
            message = "密码错误";
            return Result.builder().notOk(400, message).build();
        } catch (AuthenticationException e) {
            message = "认证失败";
            return Result.builder().notOk(400, message).build();
        }
        Object token = SecurityUtils.getSubject().getSession().getAttribute("token");
        if (token==null){
            loginUser = service.lambdaQuery().eq(LoginUser::getUsername, loginUser.getUsername()).one();
            HashMap<String, Set<String>> roleAndPermission = myRealm.getRoleAndPermission(loginUser);
            Set<String> roleNames = roleAndPermission.get("roleNames");
            Set<String> permNames = roleAndPermission.get("permNames");
            HashMap<String, String> jwt = new HashMap<>();
            jwt.put("roles", JSONObject.toJSONString(roleNames));
            jwt.put("permissions", JSONObject.toJSONString(permNames));
            jwt.put("userId", loginUser.getId());
            jwt.put("userName", loginUser.getUsername());
            token = JwtUtils.getToken(jwt);
        }
        log.info("token:{}", token);
        return Result.builder().ok().data(token).build();
    }

    @PostMapping("/logout")
    public Object logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            subject.logout();
        }
        return "redirect:/login";
    }
}
