package com.personal.skin_api.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginServiceRequest {
    private final String email;
    private final String password;

    @Builder
    private MemberLoginServiceRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }
}
