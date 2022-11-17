package com.lx.goodservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.lx.common","com.lx.goodservice"})
@MapperScan("com.lx.goodservice.dao")
public class GoodServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodServiceApplication.class, args);
    }
}
