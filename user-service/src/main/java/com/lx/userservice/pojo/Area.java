package com.lx.userservice.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lx.common.base.TreeEntity;
import lombok.*;

import java.util.Date;

/**
 * province
 *
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Area extends TreeEntity<Area> {
    private String id;

    private String treeName;
    private String parentsCode;
    /**
     * 名称
     */
    private String name;
    //这里需要声明一下，因为需要作为查询条件，利用反射获取字段，不会获取分类字段
    private String parent;

    @TableField(select = false)
    private Date createTime;

    @TableField(select = false)
    private Date updateTime;
}