package com.lx.orderservice.dto;

import com.lx.common.pojo.orderservice.GoodInfo;
import com.lx.orderservice.pojo.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderInfoDTO {
    Order order;
    List<GoodInfo> goods;
}
