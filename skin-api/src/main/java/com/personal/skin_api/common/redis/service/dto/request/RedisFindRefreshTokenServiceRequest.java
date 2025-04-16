package com.personal.skin_api.common.redis.service.dto.request;

import com.personal.skin_api.common.redis.TokenPurpose;

import com.personal.skin_api.member.repository.entity.email.Email;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RedisFindRefreshTokenServiceRequest {
    private final TokenPurpose purpose;
    private final String refreshUUID;

    @Builder
    private RedisFindRefreshTokenServiceRequest(final TokenPurpose purpose, final String refreshUUID) {
        this.purpose = purpose;
        this.refreshUUID = refreshUUID;
    }
}
