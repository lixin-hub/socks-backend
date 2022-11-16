package com.lx.goodservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.goodservice.pojo.GoodDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author LIXIN
 */
@Repository
public interface GoodDetailDao extends BaseMapper<GoodDetail> {

}