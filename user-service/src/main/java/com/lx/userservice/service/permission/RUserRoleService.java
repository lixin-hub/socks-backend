package com.lx.userservice.service.permission;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.common.base.BaseService;
import com.lx.userservice.dao.permission.RUserRoleDao;
import com.lx.userservice.dao.permission.RoleDao;
import com.lx.userservice.pojo.permission.RUserRole;
import org.springframework.stereotype.Service;

@Service
public class RUserRoleService extends ServiceImpl<RUserRoleDao,RUserRole> {
}
