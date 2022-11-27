package com.lx.userservice.controller;

import com.lx.common.base.BaseControllerImpl;
import com.lx.userservice.dao.UserInfoDao;
import com.lx.userservice.pojo.UserInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("userinfo")
public class UserinfoController extends BaseControllerImpl<UserInfoDao, UserInfo> {

    @Override
    @PostMapping("page")
    @RequiresPermissions("user:list")
    public Object selectPage(@RequestBody UserInfo entity) {
        return super.selectPage(entity);
    }

    @Override
    @GetMapping("{userId}")
    public Object selectById(@PathVariable Serializable userId) {
        return super.selectById(userId);
    }

    @Override
    @PostMapping("update")
    public Object update(@RequestBody UserInfo entity) {
        return super.updateById(entity);
    }

    @Override
    @PostMapping("insert")
    public Object insert(@RequestBody UserInfo entity) {
        return super.insert(entity);
    }

}
