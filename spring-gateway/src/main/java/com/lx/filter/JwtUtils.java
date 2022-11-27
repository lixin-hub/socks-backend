package com.lx.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    public static int times=0;
    private static String SIGNATURE = "token!@#$%^7890";

    /**
     * 生成token
     *
     * @param map //传入payload
     * @return 返回token
     */
    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        builder.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24*5));
        return builder.sign(Algorithm.HMAC256(SIGNATURE));
    }

    /**
     * 验证token
     * SignatureVerificationException //签名不一致异常
     * TokenExpiredException //令牌过期异常
     * AlgorithmMismatchException //算法不匹配异常
     * InvalidClaimException //失效的payload异常（传给客户端后，token被改动，验证不一致）
     *
     * @param token
     */
    public static void isVerify(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
        } catch (SignatureVerificationException e) {
            throw new RuntimeException("签名不一致");
        } catch (TokenExpiredException e) {
            throw new RuntimeException("令牌过期");
        } catch (AlgorithmMismatchException e) {
            throw new RuntimeException("算法不匹配");
        } catch (InvalidClaimException e) {
            throw new RuntimeException("失效的payload");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取token中payload
     *
     * @param token
     * @return
     */
    public static DecodedJWT getToken(String token) {
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }
}
