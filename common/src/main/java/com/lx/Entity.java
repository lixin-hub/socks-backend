package com.lx;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Entity<T> {
    public static final String NORMAL = "0";//正常
    public static final String DELETED = "-1";//删除
    public static final String DEPRECATED = "0";//弃用


    @TableField(exist = false)
    protected PageDto<T> page;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date updateTime;
    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;
    protected String status= String.valueOf(0);
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter(onMethod = @__(@JsonProperty))
    protected transient QueryWrapper<T> normalWrapper = new QueryWrapper<T>().eq("status", NORMAL);
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter(onMethod = @__(@JsonProperty))
    protected transient QueryWrapper<T> deletedWrapper = new QueryWrapper<T>().eq("status", DELETED);
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter(onMethod = @__(@JsonProperty))
    protected transient QueryWrapper<T> deprecatedWrapper = new QueryWrapper<T>().eq("status", DELETED);

    public abstract String getId();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public QueryWrapper<T> likeWrapper() {
        return Util.wrapper((T) this, "like");
    }public QueryWrapper<T> normalWrapper() {
        return Util.wrapper((T) this, "like");
    }

    public QueryWrapper<T> eqWrapper() {
        return Util.wrapper((T) this, "eq");
    }

    public QueryWrapper<T> neWrapper() {
        return Util.wrapper((T) this, "ne");
    }

    public QueryWrapper<T> leWrapper() {
        return Util.wrapper((T) this, "le");
    }

    public QueryWrapper<T> geWrapper() {
        return Util.wrapper((T) this, "ge");
    }

    public QueryWrapper<T> deletedWrapper() {
        return deletedWrapper;
    }

    public QueryWrapper<T> deprecatedWrapper() {
        return deprecatedWrapper;
    }

}
