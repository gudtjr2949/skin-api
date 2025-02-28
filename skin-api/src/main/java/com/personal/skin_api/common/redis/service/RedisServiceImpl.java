package com.personal.skin_api.common.redis.service;

import com.personal.skin_api.common.exception.CommonErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.redis.service.dto.request.RedisFindMailCertServiceRequest;
import com.personal.skin_api.common.redis.service.dto.request.RedisSaveMailCertServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private static final long MAIL_TTL = 3;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveMailCertification(RedisSaveMailCertServiceRequest request) {
        String key = generateKey(request.getPurpose(), request.getEmail());

        try {
            redisTemplate.opsForValue().set(key, request.getCode(), Duration.ofMinutes(MAIL_TTL));
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String findMailCertification(RedisFindMailCertServiceRequest request) {
        String key = generateKey(request.getPurpose(), request.getEmail());

        try {
            String findCode = redisTemplate.opsForValue().get(key).toString();
            return findCode;
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
        return String.join(firstKey, ":", secondKey);
    }
}
