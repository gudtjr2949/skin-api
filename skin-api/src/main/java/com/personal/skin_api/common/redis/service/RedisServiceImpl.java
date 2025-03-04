package com.personal.skin_api.common.redis.service;

import com.personal.skin_api.common.exception.CommonErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.redis.service.dto.request.*;
import com.personal.skin_api.common.security.JwtTokenConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private static final long MAIL_TTL = 3, SMS_TTL = 3;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveMailCertification(RedisSaveMailCertServiceRequest request) {
        String key = generateKey(request.getPurpose().toString(), request.getEmail());

        try {
            redisTemplate.opsForValue().set(key, request.getCode(), Duration.ofMinutes(MAIL_TTL));
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void saveSmsCertification(RedisSaveSmsCertServiceRequest request) {
        String key = generateKey(request.getPurpose().toString(), request.getPhone());

        try {
            redisTemplate.opsForValue().set(key, request.getCode(), Duration.ofMinutes(SMS_TTL));
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String findMailCertification(RedisFindMailCertServiceRequest request) {
        String key = generateKey(request.getPurpose().toString(), request.getEmail());

        try {
            String findCode = redisTemplate.opsForValue().get(key).toString();
            return findCode;
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String findSmsCertification(RedisFindSmsCertServiceRequest request) {
        String key = generateKey(request.getPurpose().toString(), request.getPhone());

        try {
            String findCode = redisTemplate.opsForValue().get(key).toString();
            return findCode;
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void saveRefreshToken(RedisSaveRefreshTokenServiceRequest request) {
        String key = generateKey(request.getPurpose().toString(), request.getEmail());
        try {
            redisTemplate.opsForValue().set(key, request.getRefreshToken(), Duration.ofMillis(JwtTokenConstant.refreshExpirationTime));
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String findRefreshToken(RedisFindRefreshTokenServiceRequest request) {
        String key = generateKey(request.getPurpose().toString(), request.getEmail());

        try {
            String findRefreshToken = redisTemplate.opsForValue().get(key).toString();
            return findRefreshToken;
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Redis에 저장할 키를 생성한다
     * @param firstKey 첫 번째 키 (목적을 나타냄)
     * @param secondKey 두 번째 키 (사용자 판별용 문자열을 나타냄)
     * @return Redis에 저장할 키
     */
    private static String generateKey(String firstKey, String secondKey) {
        return String.join(":", firstKey, secondKey);
    }
}
