package com.lx.goodservice.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lx.common.base.Entity;
import com.lx.common.base.TreeEntity;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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



}