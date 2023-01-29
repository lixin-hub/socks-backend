package com.lx.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lx.common.util.Util;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Entity<T> {
    public static final String NORMAL = "0";//正常
    public static final String DELETED = "1";//删除
    public static final String DEPRECATED = "2";//弃用


    @TableField(exist = false)
    protected PageDto<T> page;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    protected Date updateTime;
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    protected Date createTime;
    /**
     * 逻辑删除
     */
    @TableField(select = false)
    protected String status = NORMAL;
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter(onMethod = @__(@JsonProperty))
    protected transient QueryWrapper<T> normalWrapper = new QueryWrapper<T>().eq("status", NORMAL);
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter(onMethod = @__(@JsonProperty))
    protected transient QueryWrapper<T> deletedWrapper = new QueryWrapper<T>().eq("status", DELETED);
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter(onMethod = @__(@JsonProperty))
    protected transient QueryWrapper<T> deprecatedWrapper = new QueryWrapper<T>().eq("status", DEPRECATED);
    @TableField(exist = false)
    private String queryType="eq";

    public T queryType(String type) {
        this.queryType = type;
        return (T) this;
    }

    public abstract String getId();

    public QueryWrapper<T> likeWrapper() {
        return Util.wrapper((T) this, "like");
    }

    public QueryWrapper<T> normalWrapper() {
        return Util.wrapper((T) this, queryType);
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
