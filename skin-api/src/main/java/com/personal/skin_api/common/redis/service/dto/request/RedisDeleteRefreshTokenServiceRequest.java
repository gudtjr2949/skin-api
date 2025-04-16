package com.personal.skin_api.common.redis.service.dto.request;

import com.personal.skin_api.common.redis.TokenPurpose;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RedisDeleteRefreshTokenServiceRequest {

    private String refreshUUID;
    private TokenPurpose purpose;

    @Builder
    private RedisDeleteRefreshTokenServiceRequest(final String refreshUUID, final TokenPurpose purpose) {
        this.refreshUUID = refreshUUID;
        this.purpose = purpose;
    }
}
