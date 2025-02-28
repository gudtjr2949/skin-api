package com.personal.skin_api.common.redis.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void Redis에_문자열_키와_값을_저장한다() {
        // given
        String key = "gildong123@naver.com";
        String value = "53472";

        // when & then
        assertThatNoException().isThrownBy(() -> redisTemplate.opsForValue().set(key, value));
    }

    @Test
    void Redis에_저장한_값을_조회한다() {
        // given
        String key = "gildong123@naver.com";
        String value = "53472";
        redisTemplate.opsForValue().set(key, value);

        // when
        String findValue = redisTemplate.opsForValue().get(key).toString();

        // then
        assertThat(findValue).isEqualTo(value);
    }

    @Test
    void 없는_Key를_입력하면_null을_반환한다() {
        // given
        String key = "gildong123@naver.com";
        String value = "53472";
        redisTemplate.opsForValue().set(key, value);
        String wrongKey = "jack123@gmail.com";

        // when & then
        assertThat(redisTemplate.opsForValue().get(wrongKey)).isNull();
    }

    @Test
    void TTL_만료_이전에_조회하면_조회에_성공한다() throws InterruptedException {
        // given
        String key = "gildong123@naver.com";
        String value = "53472";
        long ttlInSeconds = 5; // TTL 5초

        // when
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttlInSeconds));
        sleep(4000); // 4초 대기
        String findValue = redisTemplate.opsForValue().get(key).toString();

        // then
        assertThat(findValue).isNotNull();
        assertThat(findValue).isEqualTo(value);
    }
    
    @Test
    void TTL_만료_이후에_조회하면_null이_반환된다() throws InterruptedException {
        // given
        String key = "gildong123@naver.com";
        String value = "53472";
        long ttlInSeconds = 5; // TTL 5초

        // when
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttlInSeconds));
        sleep(6000); // 6초 대기

        // then
        assertThat(redisTemplate.opsForValue().get(key)).isNull();
    }

}