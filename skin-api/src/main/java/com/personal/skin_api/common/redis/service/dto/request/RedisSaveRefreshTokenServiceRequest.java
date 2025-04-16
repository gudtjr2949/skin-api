package com.personal.skin_api.common.redis.service.dto.request;

import com.personal.skin_api.common.redis.TokenPurpose;
import com.personal.skin_api.member.repository.entity.email.Email;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RedisSaveRefreshTokenServiceRequest {
    private final TokenPurpose purpose;
    private final String refreshToken;

    @Builder
    private RedisSaveRefreshTokenServiceRequest(final TokenPurpose purpose,
                                                final String refreshToken) {
        this.purpose = purpose;
        this.refreshToken = refreshToken;
    }
}
