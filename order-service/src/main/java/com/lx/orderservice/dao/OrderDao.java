package com.lx.orderservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.orderservice.pojo.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends BaseMapper<Order> {

}