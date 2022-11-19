package com.lx.common.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class TreeEntity<T> extends Entity<T> {
    @TableField(exist = false)
    public List<T> children;
    /**
     * 分类级别
     */
    private String level;
    /**
     * 父级分类
     */
    private String parent;
//    private boolean isRoot;
    private boolean isLeaf;

    public boolean isRoot() {
        return parent==null||parent.equals("0");
    }

    public void addChild(T child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

}
