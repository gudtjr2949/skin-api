package com.personal.skin_api.common.redis.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RedisFindMailCertServiceRequest {
    private final String purpose;
    private final String email;

    @Builder
    private RedisFindMailCertServiceRequest(final String purpose, final String email) {
        this.purpose = purpose;
        this.email = email;
    }
}
