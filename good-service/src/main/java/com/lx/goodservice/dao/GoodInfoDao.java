package com.lx.goodservice.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lx.goodservice.pojo.GoodInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LIXIN
 */
@Repository
public interface GoodInfoDao extends BaseMapper<GoodInfo> {
//    @Override
//    <E extends IPage<GoodInfo>> E selectPage(E page, Wrapper<GoodInfo> queryWrapper);
}