package com.personal.skin_api.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberModifyPasswordServiceRequest {
    private final String email;
    private final String newPassword;

    @Builder
    private MemberModifyPasswordServiceRequest(final String email, final String newPassword) {
        this.email = email;
        this.newPassword = newPassword;
    }
}
