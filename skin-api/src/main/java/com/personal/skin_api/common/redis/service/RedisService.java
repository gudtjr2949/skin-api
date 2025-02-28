package com.personal.skin_api.common.redis.service;

import com.personal.skin_api.common.redis.service.dto.request.RedisFindMailCertServiceRequest;
import com.personal.skin_api.common.redis.service.dto.request.RedisSaveMailCertServiceRequest;

public interface RedisService {
    void saveMailCertification(RedisSaveMailCertServiceRequest request);
    String findMailCertification(RedisFindMailCertServiceRequest request);
}
