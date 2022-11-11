package com.lx.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@Slf4j
public class TestController {
    @GetMapping("test/{userId}")
    public Object test(@PathVariable("userId") String userId) {
        log.debug("user-service");
        return "用户:" + userId;
    }
}
