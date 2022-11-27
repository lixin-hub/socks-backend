package com.lx.userservice.service.permission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.common.base.BaseService;
import com.lx.userservice.dao.permission.RUserRoleDao;
import com.lx.userservice.dao.permission.RoleDao;
import com.lx.userservice.pojo.UserInfo;
import com.lx.userservice.pojo.permission.RUserRole;
import com.lx.userservice.pojo.permission.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RUserRoleService extends ServiceImpl<RUserRoleDao,RUserRole> {
    @Autowired
    RoleService roleService;
    public List<Role> getRolesByUserId(UserInfo userInfo) {
        Set<String> roleCodes = list(new QueryWrapper<RUserRole>().eq("user_id",userInfo.getId()))
                .stream()
                .map(RUserRole::getRoleCode)
                .collect(Collectors.toSet());
        return  roleService.listByIds(roleCodes);

    }
}
