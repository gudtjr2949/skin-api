package com.personal.skin_api.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberFindPasswordServiceRequest {
    private final String email;
    private final String phone;

    @Builder
    private MemberFindPasswordServiceRequest(final String email, final String phone) {
        this.email = email;
        this.phone = phone;
    }
}
