package com.lx.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(1)
@Slf4j
public class TokenFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取请求参数
        MultiValueMap<String, String> params = exchange.getRequest().getHeaders();
        // 2.获取authorization参数
        String auth = params.getFirst("Authorization");

        // 3.校验
        log.info(auth);
        JwtUtils.times++;
        log.info("次数:{}",JwtUtils.times);
        try {
            JwtUtils.isVerify(auth);         // 放行
            return chain.filter(exchange);
        }catch (Exception e){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // 4.拦截
            // 4.1.禁止访问，设置状态码
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // 4.2.结束处理
            return exchange.getResponse().setComplete();
        }

    }
}
