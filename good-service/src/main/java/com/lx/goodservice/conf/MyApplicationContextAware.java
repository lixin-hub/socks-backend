package com.lx.goodservice.conf;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class MyApplicationContextAware implements ApplicationContextAware {

    @Getter
    private  ApplicationContext applicationContext;

    public  Object getBean(String name) throws BeansException {

    return applicationContext.getBean(name);
    }

    public  <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext application) throws BeansException {
        applicationContext = application;
    }
}
