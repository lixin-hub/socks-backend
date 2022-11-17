package com.lx.common.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public abstract class TreeEntity<T> extends Entity<T> {
    /**
     * 分类级别
     */
    private String level;
    /**
     * 父级分类
     */
    private String parent;
    @TableField(exist = false)
    public List<T> children;

    public void addChild(T child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

}
