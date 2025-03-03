package com.personal.skin_api.common.redis.service;

import com.personal.skin_api.common.redis.service.dto.request.RedisFindMailCertServiceRequest;
import com.personal.skin_api.common.redis.service.dto.request.RedisFindSmsCertServiceRequest;
import com.personal.skin_api.common.redis.service.dto.request.RedisSaveMailCertServiceRequest;
import com.personal.skin_api.common.redis.service.dto.request.RedisSaveSmsCertServiceRequest;

public interface RedisService {
    void saveMailCertification(RedisSaveMailCertServiceRequest request);
    void saveSmsCertification(RedisSaveSmsCertServiceRequest request);
    String findMailCertification(RedisFindMailCertServiceRequest request);
    String findSmsCertification(RedisFindSmsCertServiceRequest request);
}
