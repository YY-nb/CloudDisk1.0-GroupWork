package com.project.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {
    /**
     * token过期时间
     */
    private static final long EXPIRE_TIME = 24 *60 * 60 * 1000;
    /**
     * token秘钥
     */
    private static final String TOKEN_SECRET = "peopleinwesttwoonlinearehandsomeandbeautiful";
    /**
     * 加密算法
     */
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(TOKEN_SECRET);


    /**
     * 生成签名，30分钟过期
     * @param userMes 用户信息
     * @param loginTime 登录时间
     * @return 生成的token
     */
    public static String sign(String userMes, String loginTime) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    .withClaim("loginMes", userMes)
                    .withClaim("loginTime", loginTime)
                    .withExpiresAt(date)
                    .sign(ALGORITHM);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 检验token是否正确
     * @param token 需要校验的token
     * @return 校验是否成功
     */
    public static boolean verify(String token){
        try {

            JWTVerifier verifier = JWT.require(ALGORITHM).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
