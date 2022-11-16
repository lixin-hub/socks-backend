package com.lx.common.aop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.common.annotation.EntityField;
import com.lx.common.base.BaseService;
import com.lx.common.util.MyApplicationContextAware;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author LIXIN\
 * 仅作用于findbyid
 */
@Aspect
@Component
@Slf4j
public class AutoFillAccept {
    @Autowired
    MyApplicationContextAware myApplicationContextAware;

    @Pointcut("@annotation(com.lx.common.annotation.AutoFill)")
    public void joinTable() {

    }

    @SneakyThrows
    @AfterReturning(returning = "ret", value = "joinTable()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
        Object id = joinPoint.getArgs()[0];
        if (id==null) {
            return;
        }
        Class<?> returnType =ret.getClass();
        Field[] declaredFields = returnType.getDeclaredFields();
        QueryWrapper<?> wrapper = new QueryWrapper<>();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            EntityField annotation = declaredField.getAnnotation(EntityField.class);
            if (annotation != null) {
                Class<?> service = annotation.service();
                wrapper.eq("id", id);
                BaseService baseService = ((BaseService) myApplicationContextAware.getBean(service));
                Object entity = baseService.selectById((Serializable) id);
                declaredField.set(ret,entity);
                System.out.println(entity);
            }
        }

    }


}
