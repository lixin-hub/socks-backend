package com.lx.common.util;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Util {
    /**
     * @param entity 实体
     * @param <T>    类型
     * @return 如果对象为空，创建一新的
     */
    public static <T> T newIfNull(T entity) {
        log.debug("entity->" + entity);
        if (entity != null) return entity;
        entity = (T) new Object();
        return entity;
    }
    public static <T> QueryWrapper<T> wrapper(T t, final String type) {
        Class<T> clazz = (Class<T>) t.getClass();
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            Object value;
            declaredField.setAccessible(true);
            try {
                value = declaredField.get(t);
            } catch (IllegalAccessException e) {
                continue;
            }
            if (value == null) continue;
            TableField annotation = declaredField.getAnnotation(TableField.class);
            if (annotation != null) {
                if (!annotation.exist()) {
                    continue;
                }
                name = Objects.equals(annotation.value(), "") ? name : annotation.value();
            }

            name = humpToUnderline(name);
            log.info("name:{},type:{},value:{}", name, type, value);
            switch (type) {
                case "eq":
                    wrapper = wrapper.eq(name, value);
                    break;
                case "ne":
                    wrapper = wrapper.ne(name, value);
                    break;
                case "le":
                    wrapper = wrapper.ge(name, value);
                    break;
                case "ge":
                    wrapper = wrapper.le(name, value);
                    break;
                case "like":
                    wrapper = wrapper.like(name, value);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        }
        /* return wrapper.eq("status",Entity.NORMAL); */

        return wrapper;
    }

    /**
     * @param str 源文本
     * @return 驼峰转下划线
     */
    public static String humpToUnderline(String str) {
        String regex = "([A-Z])";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find()) {
            String target = matcher.group();
            str = str.replaceAll(target, "_" + target.toLowerCase());
        }
        return str;
    }
}
