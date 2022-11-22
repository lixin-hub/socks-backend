package com.lx.userservice.service.permission;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.common.util.Util;
import com.lx.userservice.dao.permission.RoleDao;
import com.lx.userservice.pojo.permission.Permission;
import com.lx.userservice.pojo.permission.RRolePermission;
import com.lx.userservice.pojo.permission.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService extends ServiceImpl<RoleDao, Role> {
    @Autowired
    PermissionService permissionService;
    @Autowired
    RRolePermissionService rRolePermissionService;

    // TODO: 2022/11/21 针对这种读多写少的，后期采用redis缓存实现
    public PageDto<Role> tree(Role role) {
        PageDto<Role> page = super.page(role.getPage());
        //角色包含的权限id
        Set<String> pis = new HashSet<>(page.getRecords().size());
        HashMap<Role, Set<String>> hashMap = new HashMap<>();
        page.getRecords().forEach(item -> {
            Set<String> rapids = rRolePermissionService.lambdaQuery().eq(RRolePermission::getRoleCode, item.getId()).list()
                    .stream()
                    .map(RRolePermission::getPermissionCode).collect(Collectors.toSet());
            hashMap.put(item, pis);
            pis.addAll(rapids);
        });
        List<Role> roles = new ArrayList<>(page.getRecords().size());
        hashMap.forEach((k, v) -> {
            //查询角色对应的所有权限
            if (v.size() > 0) {
                List<Permission> hashPermission = permissionService.listByIds(v);
                Set<Permission> root = hashPermission.stream().filter(i -> "0".equals(i.getParent())).collect(Collectors.toSet());
                k.setPermissions(Util.toTree(hashPermission, root));
            }
            roles.add(k);
        });
        page.setRecords(roles);
        return page;

    }
}
