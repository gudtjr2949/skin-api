package com.personal.skin_api.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginServiceRequest {
    private final String email;
    private final String password;
    private String accessToken;

    @Builder
    private MemberLoginServiceRequest(final String email, final String password, final String accessToken) {
        this.email = email;
        this.password = password;
        this.accessToken = accessToken;
    }
}
