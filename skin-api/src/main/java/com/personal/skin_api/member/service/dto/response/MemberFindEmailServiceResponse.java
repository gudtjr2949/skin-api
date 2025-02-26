package com.personal.skin_api.member.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberFindEmailServiceResponse {
    private final String email;

    @Builder
    private MemberFindEmailServiceResponse(final String email) {
        this.email = email;
    }
}
