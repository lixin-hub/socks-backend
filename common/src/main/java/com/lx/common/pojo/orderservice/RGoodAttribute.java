package com.lx.common.pojo.orderservice;

import com.lx.common.base.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * r_good_attribute 商品属性关联表
 * @author lixin
 */
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RGoodAttribute extends Entity<RGoodAttribute> {
    private String id;

    private String attrId;

    private String goodId;

    private String attrType;

    private String value;

}