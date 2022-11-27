package com.lx.common.conf;

import com.lx.common.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object excep(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        return Result.builder().notOk(500, e.getMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public Object excep1(HttpServletRequest req, IllegalArgumentException e) {
        log.error("发生异常：{}", e.getMessage());
        e.printStackTrace();
        return Result.builder().notOk(400, e.getMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler(IllegalStateException.class)
    public Object excep2(HttpServletRequest req, IllegalStateException e) {
        log.error("发生异常：{}", e.getMessage());
        e.printStackTrace();
        return Result.builder().notOk(400, e.getMessage()).build();
    }
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public Object excep3(HttpServletRequest req, UnauthorizedException e) {
        log.error("发生异常：{}", e.getMessage());
        e.printStackTrace();
        return Result.builder().notOk(400,"没有权限访问").build();
    }
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Object excep3(HttpServletRequest req, RuntimeException e) {
        log.error("发生异常：{}", e.getMessage());
        e.printStackTrace();
        return Result.builder().notOk(400,e.getMessage()).build();
    }
}
