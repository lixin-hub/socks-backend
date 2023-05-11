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
        return Result.builder().notOk(500, "发送未知错误，请等待管理员修复！").build();
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public Object excep1(HttpServletRequest req, IllegalArgumentException e) {
        log.error("发生异常：{}", e.getMessage());
        e.printStackTrace();
        return Result.builder().notOk(400, "参数异常，请检查参数！").build();
    }

    @ResponseBody
    @ExceptionHandler(IllegalStateException.class)
    public Object excep2(HttpServletRequest req, IllegalStateException e) {
        log.error("发生异常：{}", e.getMessage());
        e.printStackTrace();
        return Result.builder().notOk(400, "该请求状态异常，请稍候重试！").build();
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
        return Result.builder().notOk(400,"发生运行时错误！请稍候再试").build();
    }
}
