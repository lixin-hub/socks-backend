package com.lx.goodservice.dao;


import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.goodservice.pojo.GoodPrice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author LIXIN
 */
@Repository
public interface GoodPriceDao extends BaseMapper<GoodPrice> {
    GoodPrice selectByGoodId(@Param("id") Serializable id);
}