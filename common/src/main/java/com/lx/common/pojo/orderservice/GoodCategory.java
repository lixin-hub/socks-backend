package com.lx.common.pojo.orderservice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lx.common.base.TreeEntity;
import lombok.*;

/**
 * good_category
 *
 * @author lixin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GoodCategory extends TreeEntity<GoodCategory> {
    /**
     * 分类id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 分类名称
     */
    private String name;

    private String note;


}