package com.personal.skin_api.common.redis.service.dto.request;

import com.personal.skin_api.common.redis.TokenPurpose;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RedisDeleteRefreshTokenServiceRequest {

    private String email;
    private TokenPurpose purpose;

    @Builder
    private RedisDeleteRefreshTokenServiceRequest(final String email, final TokenPurpose purpose) {
        this.email = email;
        this.purpose = purpose;
    }
}
