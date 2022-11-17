package com.lx.goodservice.pojo;

import com.lx.common.base.Entity;
import lombok.*;

/**
 * good_attribute
 *
 * @author lixin
 */
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GoodAttribute extends Entity<GoodAttribute> {
    /**
     * 属性id
     */
    private String id;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 分类id
     */
    private String catId;

    /**
     * 属性类型：0表示商品属性：1表示商品参数（tag）
     */
    private String attrType;

    /**
     * 值，多个值用空格隔开
     */
    private String value;


}