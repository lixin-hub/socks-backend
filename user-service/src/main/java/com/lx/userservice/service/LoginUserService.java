package com.lx.userservice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.userservice.dao.LoginUserDao;
import com.lx.userservice.pojo.LoginUser;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService extends ServiceImpl<LoginUserDao,LoginUser> {

}
