package com.lx.goodservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.goodservice.pojo.GoodInfo;
import org.springframework.stereotype.Repository;

/**
 * @author LIXIN
 */
@Repository
public interface GoodInfoDao extends BaseMapper<GoodInfo> {
//    @Override
//    <E extends IPage<GoodInfo>> E selectPage(E page, Wrapper<GoodInfo> queryWrapper);
}