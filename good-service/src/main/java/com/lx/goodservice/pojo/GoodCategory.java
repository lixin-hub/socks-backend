package com.lx.goodservice.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lx.common.base.Entity;
import lombok.*;

/**
 * good_category
 * @author lixin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class GoodCategory extends Entity<GoodCategory> implements Serializable {
    /**
     * 分类id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父级分类
     */
    private String parent;

    private Date createTime;

    private Date updateTime;

    private String status;

    /**
     * 分类级别
     */
    private String level;

    private static final long serialVersionUID = 1L;
}