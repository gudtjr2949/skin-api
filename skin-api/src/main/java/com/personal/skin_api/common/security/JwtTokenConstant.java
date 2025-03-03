package com.personal.skin_api.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenConstant {

    public static String secretKey;
    public static long accessExpirationTime;
    public static long refreshExpirationTime;

    @Value("${spring.jwt.secret}")
    public void setSecretKey(String secretKey) {
        JwtTokenConstant.secretKey = secretKey;
    }

    @Value("${spring.jwt.token.access-expiration-time}")
    public void setAccessExpirationTime(long accessExpirationTime) {
        JwtTokenConstant.accessExpirationTime = accessExpirationTime;
    }

    @Value("${spring.jwt.token.refresh-expiration-time}")
    public void setRefreshExpirationTime(long refreshExpirationTime) {
        JwtTokenConstant.refreshExpirationTime = refreshExpirationTime;
    }
}