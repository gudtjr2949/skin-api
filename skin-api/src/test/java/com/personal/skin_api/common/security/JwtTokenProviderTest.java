package com.personal.skin_api.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void AccessToken과_RefreshToken을_생성하고_만료일자를_비교한다() {
        // given
        String email = "test@naver.com";

        // when
        String accessToken = jwtTokenProvider.generateJwt(email, JwtTokenConstant.accessExpirationTime);
        Claims claimsOfAccessToken = Jwts.parser()
                .setSigningKey(JwtTokenConstant.secretKey)
                .parseClaimsJws(accessToken)
                .getBody();

        String refreshToken = jwtTokenProvider.generateJwt(email, JwtTokenConstant.refreshExpirationTime);
        Claims claimsOfRefreshToken = Jwts.parser()
                .setSigningKey(JwtTokenConstant.secretKey)
                .parseClaimsJws(refreshToken)
                .getBody();


        // then
        assertThat(accessToken).isNotNull();
        assertThat(email).isEqualTo(claimsOfAccessToken.getSubject());
        assertThat(email).isEqualTo(claimsOfRefreshToken.getSubject());
        assertThat(claimsOfAccessToken.getExpiration()).isBefore(claimsOfRefreshToken.getExpiration());
    }

    @Test
    void Token이_만료여부를_확인한다() throws InterruptedException {
        // given
        String email = "test@naver.com";

        // when
        String token = jwtTokenProvider.generateJwt(email, 2000); // 1000밀리초 -> 1초
        Claims claims = Jwts.parser()
                .setSigningKey(JwtTokenConstant.secretKey)
                .parseClaimsJws(token)
                .getBody();
        Date expiration = claims.getExpiration();  // 만료 시간
        Thread.sleep(3000); // 1초 대기

        // then
        assertThat(new Date()).isAfter(expiration);
    }
}