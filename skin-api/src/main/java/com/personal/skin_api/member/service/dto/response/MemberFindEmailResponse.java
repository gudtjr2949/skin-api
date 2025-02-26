package com.personal.skin_api.member.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberFindEmailResponse {
    private final String email;

    @Builder
    private MemberFindEmailResponse(final String email) {
        this.email = email;
    }
}
