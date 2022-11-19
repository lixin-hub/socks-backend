package com.lx.userservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.common.base.SpiceBaseMapper;
import com.lx.userservice.pojo.Area;
import org.springframework.stereotype.Repository;

/**
 * AreaDao继承基类
 */
@Repository
public interface AreaDao extends SpiceBaseMapper<Area> {
}