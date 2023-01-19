package com.lx.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Component
@Order(1)
@Slf4j
public class TokenFilter implements GlobalFilter {
    static Set<String> filters;

    static {
        filters = new HashSet<>();
        filters.add("/auth");
        filters.add("/file");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取请求参数
        MultiValueMap<String, String> params = exchange.getRequest().getHeaders();
        // 2.获取authorization参数
        String auth = params.getFirst("Authorization");

        // 3.校验
        log.info("获取authorization参数：{}",auth);
        JwtUtils.times++;
        log.info("处理请求次数:{}", JwtUtils.times);
        try {
            //如果没有token判断是不是文件服务来的
            if (StringUtils.isBlank(auth)) {
                String s = exchange.getRequest().getPath().toString();
                log.info("请求路径：{}",s);
                for (String filter : filters) {
                    if (s.startsWith(filter)) {
                        return chain.filter(exchange);
                    }
                }
            }
            JwtUtils.isVerify(auth);         // 放行
            return chain.filter(exchange);
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // 4.拦截
            // 4.1.禁止访问，设置状态码
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            // 4.2.结束处理
            return exchange.getResponse().setComplete();
        }

    }
}
