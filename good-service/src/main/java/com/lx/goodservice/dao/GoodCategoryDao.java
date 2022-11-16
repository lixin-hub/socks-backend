package com.lx.goodservice.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.goodservice.pojo.GoodCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LIXIN
 */
@Repository
public interface GoodCategoryDao extends BaseMapper<GoodCategory> {

}
