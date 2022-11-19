package com.lx.orderservice.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lx.common.base.Entity;
import lombok.*;

/**
 * order
 * @author 
 */
@Data
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("m_order")//order是关键字，所以加个前缀
public class Order extends Entity<Order> {
    /**
     * 订单流水号
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 是否发货 0：未发货 1:发货
     */
    private Object isSend;

    private Date createTime;

    /**
     * 支付状态 0：未支付 1：支付
     */
    private Object isPay;

    /**
     * 第三方支付流水号
     */
    private String tradeNo;

    /**
     * 备注
     */
    private String note;


    /**
     * 订单总价
     */
    private Double totalPrice;
    //地址
    private String address;
    private String detailAddr;
}