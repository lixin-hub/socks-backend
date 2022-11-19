package com.lx.orderservice.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lx.common.base.Entity;
import com.lx.common.pojo.orderservice.GoodInfo;
import lombok.*;

/**
 * r_order_good
 * @author 
 */
@Data
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ROrderGood extends Entity<ROrderGood> {
    private String id;

    private String orderId;

    private String goodId;
    @TableField(exist = false)
    private GoodInfo goodInfo;

    private Integer goodNum;
    /**
     * 同类商品的总价
     */
    private Double goodsTotalPrice;

}