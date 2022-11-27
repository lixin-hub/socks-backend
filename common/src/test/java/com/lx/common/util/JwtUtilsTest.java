package com.lx.common.util;

import com.alibaba.fastjson.JSONArray;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
@Slf4j
public class JwtUtilsTest {

    @Test
    public void getToken() {
        HashMap<String, String> map = new HashMap<>();
//        JSONArray array=new JSONArray();
//        array.add("11");
//        array.add("12");
//        array.add("13");
//        String value = array.toJSONString();
        List<String> array=new ArrayList<>();
        array.add("11");
        array.add("12");
        array.add("13");
        String value = array.toString();
        log.info(value);
        map.put("name", value);
        map.put("pass","123");
        String token = JwtUtils.getToken(map);
        log.info(token);
        DecodedJWT decodedJWT = JwtUtils.getToken(token);
        Claim name = decodedJWT.getClaim("name");
        System.out.println(name);
        List<String> strings = JSONArray.parseArray(name.asString(),String.class);
        System.out.println(strings);

    }

    @Test
    public void isVerify() {
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwZXJtaXNzaW9ucyI6IltdIiwicm9sZXMiOiJbXSIsInVzZXJOYW1lIjoiYWRtaW4iLCJleHAiOjE2Njk5NjQ0MzMsInVzZXJJZCI6IjE1OTYwNzEwMTEwMzk4MjU5MjEifQ.T1RFAVWjGaCwBHixaKlO30s3spquqe4ZnPzHWNyiH54";
        JwtUtils.isVerify(token);

        DecodedJWT decodedJWT = JwtUtils.getToken(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        claims.forEach((k,v)->{
            log.info("key:{},v:{}",k,v.asString());
        });
    }

    @Test
    public void testGetToken() {
    }
}