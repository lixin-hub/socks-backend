package com.lx.userservice.controller;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONArray;
import com.lx.common.base.BaseControllerImpl;
import com.lx.common.base.Result;
import com.lx.common.util.JwtUtils;
import com.lx.common.util.Util;
import com.lx.userservice.conf.MyRealm;
import com.lx.userservice.dao.LoginUserDao;
import com.lx.userservice.pojo.LoginUser;
import com.lx.userservice.pojo.Menu;
import com.lx.userservice.pojo.UserInfo;
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
    @Autowired
    TimedCache<String, String> verificationCodeCache;

    /**
     * @param loginUser 用户名和密码
     * @param tempToken 临时token
     * @param code      验证码
     * @return
     */
    @PostMapping("register/{tempToken}/{code}")
    public Object register(@RequestBody LoginUser loginUser, @PathVariable String tempToken, @PathVariable String code) {
        if (StringUtils.isBlank(code)) {
            return Result.builder().notOk(400, "请输入验证码").build();
        }
        String tCode = verificationCodeCache.get(tempToken);
        if (tCode == null) {
            return Result.builder().notOk(400, "验证码已过期").build();
        }
        if (!tCode.equals(code)) {
            return Result.builder().notOk(400, "验证码错误").build();
        }
        UserInfo newUser;
        try {
            newUser = ((LoginUserService) service).register(loginUser);
        } catch (IllegalArgumentException e) {
            return Result.builder().data(loginUser).notOk(400, e.getMessage()).build();
        } catch (IllegalStateException e) {
            return Result.builder().data(loginUser).notOk(500, e.getMessage()).build();
        }
        return Result.builder().data(newUser).ok("注册成功").build();
    }

    //当打开网页的时候请求该接口下发临时token
    @GetMapping("visit")
    public Object visited() {
        return Result.builder().ok().data(IdUtil.randomUUID()).build();
    }

    @PostMapping("login")
    public Object login(@RequestBody LoginUser loginUser) {
//        String pass = EncryptUtils.md5Base64(loginUser.getPassword());
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
        String username = loginUser.getUsername();
        if (Util.isPhone(username)) {
            UserInfo info = userInfoService.lambdaQuery().eq(UserInfo::getPhone, username).one();
            loginUser = service.getById(info.getId());
        } else {
            loginUser = service.lambdaQuery().eq(LoginUser::getUsername, loginUser.getUsername()).one();
        }
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

    @GetMapping("/hasPhone/{phone}")
    public Object hasPhone(@PathVariable String phone) {
        if (StringUtils.isBlank(phone))
            return Result.builder().notOk(400, "参数为空").build();
        boolean hasUser = userInfoService.hasPhone(phone);
        return Result.builder().data(hasUser).ok().build();
    }
    @GetMapping("/hasUser/{username}")
    public Object hasUser(@PathVariable String username) {
        if (StringUtils.isBlank(username))
            return Result.builder().notOk(400, "参数为空").build();
        boolean hasUser = service.lambdaQuery().eq(LoginUser::getUsername,username).one()!=null;
        return Result.builder().data(hasUser).ok().build();
    }

    @GetMapping("/sendCode/")
    public Object sendCode(@RequestParam("p") String phone, @RequestParam("t") String tempToken) {
        if (StringUtils.isBlank(phone))
            return Result.builder().notOk(400, "参数为空").build();
        ((LoginUserService) service).sendCode(phone, tempToken);
        return Result.builder().ok("发送成功").build();
    }


}
