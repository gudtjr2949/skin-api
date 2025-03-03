package com.personal.skin_api.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${spring.jwt.secret}")
    private String secretKey;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    /**
     * JWT 토큰을 생성한다.
     * @param email JWT 토큰 생성에 사용할 사용자 이메일
     * @return JWT 토큰
     */
    public String generateJwt(String email, long expirationTime) {
        Claims claims = Jwts.claims().setSubject(email);

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
