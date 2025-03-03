package com.personal.skin_api.common.redis.service;

import com.personal.skin_api.common.redis.service.dto.request.*;

public interface RedisService {
    void saveMailCertification(RedisSaveMailCertServiceRequest request);
    void saveSmsCertification(RedisSaveSmsCertServiceRequest request);
    String findMailCertification(RedisFindMailCertServiceRequest request);
    String findSmsCertification(RedisFindSmsCertServiceRequest request);
    void saveRefreshToken(RedisSaveRefreshTokenServiceRequest request);
    String findRefreshToken(RedisFindRefreshTokenServiceRequest email);
}
