package com.lx.userservice.service;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.common.util.Util;
import com.lx.userservice.dao.LoginUserDao;
import com.lx.userservice.pojo.LoginUser;
import com.lx.userservice.pojo.Menu;
import com.lx.userservice.pojo.UserInfo;
import com.lx.userservice.pojo.permission.RRolePermission;
import com.lx.userservice.pojo.permission.RUserRole;
import com.lx.userservice.service.permission.PermissionService;
import com.lx.userservice.service.permission.RRolePermissionService;
import com.lx.userservice.service.permission.RUserRoleService;
import com.lx.userservice.service.permission.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoginUserService extends ServiceImpl<LoginUserDao, LoginUser> {

    @Autowired
    RoleService roleService;
    @Autowired
    TimedCache<String, String> verificationCodeCache;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RRolePermissionService rRolePermissionService;
    @Autowired
    RUserRoleService rUserRoleService;

    @Autowired
    @Lazy
    UserInfoService userInfoService;

    //验证码缓存
    @Bean
    public static <K, V> TimedCache<K, V> verificationCodeCache() {
        return CacheUtil.newTimedCache(50 * 1000);
    }

    public Set<Menu> getMenus(String userId) {
        LoginUser byId = getById(userId);
        Objects.requireNonNull(byId);
        Set<String> roleIds = rUserRoleService.lambdaQuery()
                .eq(RUserRole::getUserId, userId).list()
                .stream().map(RUserRole::getRoleCode)
                .collect(Collectors.toSet());
        if (roleIds.size() == 0) return null;
        try {
            Set<String> permissionIds = rRolePermissionService.lambdaQuery()
                    .in(RRolePermission::getRoleCode, roleIds).list()
                    .stream().map(RRolePermission::getPermissionCode)
                    .collect(Collectors.toSet());
            if (permissionIds.size() == 0) return null;

            Set<Menu> permissions = permissionService.listByIds(permissionIds).stream()
                    .map(permission -> Menu.builder()
                            .parent(permission.getParent())
                            .name(permission.getDescription())
                            .path(permission.getPath())
                            .id(permission.getId())
                            .build())
                    .collect(Collectors.toSet());
            if (permissions.size() == 0) return null;
            Set<Menu> collect = permissions.stream().filter(Menu::isRoot).collect(Collectors.toSet());
//            permissions.forEach(System.out::println);
            return new HashSet<>(Util.toTree(permissions, collect));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("抱歉，未知错误！", e.getCause());
        }
    }

    /**
     * @param loginUser 注册参数
     * @return 注册的新用户
     */
    @Transactional(rollbackFor = Exception.class)
    public UserInfo register(LoginUser loginUser) throws IllegalArgumentException, IllegalStateException {
        boolean isPhone = Util.isPhone(loginUser.getUsername());
        if (StringUtils.isBlank(loginUser.getUsername())) {
            throw new IllegalArgumentException("用户名或手机号为空");
        }
        if (!isPhone) {
            throw new IllegalArgumentException("手机号不合法");
        }
        boolean hasPhone = userInfoService.hasPhone(loginUser.getUsername());
        if (hasPhone) {
            throw new IllegalStateException("该手机号码已经注册过");
        }
        if (StringUtils.isBlank(loginUser.getPassword())) {
            throw new IllegalArgumentException("密码为空");
        }
        // 密码加密
//        String pass = EncryptUtils.md5Base64(loginUser.getPassword());
        String pass =loginUser.getPassword();

        UserInfo userInfo = UserInfo.builder().id(loginUser.getId()).username(genUserName()).password(pass).phone(loginUser.getUsername()).build();
        boolean save = userInfoService.save(userInfo);
        if (!save) {
            throw new IllegalStateException("注册失败");
        }
        return userInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        userInfoService.removeById(id);
        return super.removeById(id);
    }

    /**
     * @return 生用户名
     */
    private String genUserName() {
        int count = super.count();
        Random random1 = new Random();
        int random = random1.nextInt(100) + 100;
        return "袜-" + random + "" + count;
    }

    public void sendCode(String phone,String tempToken) {
        String code = "1234";
        send(phone, code);
        verificationCodeCache.put(tempToken, code);
    }

    private void send(String phone, String code) {
    }
}
