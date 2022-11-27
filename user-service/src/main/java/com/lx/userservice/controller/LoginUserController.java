package com.lx.userservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.lx.common.base.BaseControllerImpl;
import com.lx.common.base.Result;
import com.lx.common.util.JwtUtils;
import com.lx.userservice.conf.MyRealm;
import com.lx.userservice.dao.LoginUserDao;
import com.lx.userservice.pojo.LoginUser;
import com.lx.userservice.pojo.Menu;
import com.lx.userservice.pojo.permission.RUserRole;
import com.lx.userservice.service.LoginUserService;
import com.lx.userservice.service.UserInfoService;
import com.lx.userservice.service.permission.RUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    RUserRoleService rUserRoleService;

    @PostMapping("register")
    public Object register(@RequestBody LoginUser loginUser) {
        // 密码加密
        String pass = EncryptUtils.md5Base64(loginUser.getPassword());
        loginUser.setPassword(pass);
        return super.insert(loginUser);
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
        loginUser = service.lambdaQuery().eq(LoginUser::getUsername, loginUser.getUsername()).one();

        HashMap<String, Set<String>> roleAndPermission = userInfoService.getRoleAndPermission(loginUser);
        Set<String> roleNames = roleAndPermission.get("roleNames");
        Set<String> permNames = roleAndPermission.get("permNames");
        HashMap<String, String> jwt = new HashMap<>();
        jwt.put("roles", JSONArray.toJSONString(roleNames));
        jwt.put("permissions", JSONArray.toJSONString(permNames));
        jwt.put("userId", loginUser.getId());
        jwt.put("userName", loginUser.getUsername());
        jwt.forEach(log::info);
        String token = JwtUtils.getToken(jwt);
        log.info("token:{}", token);
        JwtUtils.isVerify(token);
        return Result.builder().ok().data(token).message(loginUser.getId()).build();
    }

    @PostMapping("/logout")
    public Object logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            subject.logout();
        }
        return "redirect:/login";
    }

    @GetMapping("/menus/{userId}")
    public Object getMenus(@PathVariable String userId) {
        if (userId == null || StringUtils.isBlank(userId)) {
            return Result.builder().notOk(400, "参数错误").build();
        }
        Set<Menu> menus = ((LoginUserService) service).getMenus(userId);
        return Result.builder().ok().data(menus).build();
    }

    @GetMapping("{userId}/role/{roleId}")
    public Object mallocRoles(@PathVariable String userId, @PathVariable String roleId) {
        if (userId == null || roleId == null)
            return Result.builder().notOk(400, "参数错误").build();
        rUserRoleService.save(RUserRole.builder().roleCode(roleId).userId(userId).build());
        return Result.builder().ok().build();
    }
}
