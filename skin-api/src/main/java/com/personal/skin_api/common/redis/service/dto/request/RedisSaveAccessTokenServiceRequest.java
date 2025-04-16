package com.personal.skin_api.common.redis.service.dto.request;

import com.personal.skin_api.common.redis.TokenPurpose;
import com.personal.skin_api.member.repository.entity.email.Email;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RedisSaveAccessTokenServiceRequest {
    private final TokenPurpose purpose;
    private final Email email;
    private final String accessToken;

    @Builder
    private RedisSaveAccessTokenServiceRequest(
            final TokenPurpose purpose,
            final String email,
            final String accessToken) {
        this.purpose = purpose;
        this.email = new Email(email);
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email.getEmail();
    }
}
