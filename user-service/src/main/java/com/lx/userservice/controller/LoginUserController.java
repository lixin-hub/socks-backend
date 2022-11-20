package com.lx.userservice.controller;

import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.lx.common.base.BaseControllerImpl;
import com.lx.common.base.Result;
import com.lx.userservice.dao.LoginUserDao;
import com.lx.userservice.pojo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("login")
@RestController
public class LoginUserController extends BaseControllerImpl<LoginUserDao, LoginUser> {

    @PostMapping("register")
    public Object register(@RequestBody LoginUser loginUser) {
        // 密码加密
        String pass = EncryptUtils.md5Base64(loginUser.getPassword());
        loginUser.setPassword(pass);
//        return super.insert(loginUser);
        return null;
    }

    @PostMapping("login")
    public Object login(@RequestParam String username, @RequestParam String password, boolean rememberMe) {
        // 1.创建UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        // 使用记住我功能
        usernamePasswordToken.setRememberMe(rememberMe);
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
        Object rememberMe1 = subject.getSession().getAttribute("rememberMe");
        return Result.builder().ok().data(rememberMe1).build();
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
