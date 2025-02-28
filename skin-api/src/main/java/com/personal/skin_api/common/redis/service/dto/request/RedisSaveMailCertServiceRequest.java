package com.personal.skin_api.common.redis.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RedisSaveMailCertServiceRequest {
    private final String purpose;
    private final String email;
    private final String code;

    @Builder
    private RedisSaveMailCertServiceRequest(final String purpose, final String email, final String code) {
        this.purpose = purpose;
        this.email = email;
        this.code = code;
    }
}
