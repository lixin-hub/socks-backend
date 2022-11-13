package com.lx;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootGatewayApplication.class, args);
    }
    @Bean
    public IRule rule(){
        return new RandomRule();
    }
}
