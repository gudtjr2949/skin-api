package com.personal.skin_api.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberFindDetailServiceRequest {
    private final String email;

    @Builder
    private MemberFindDetailServiceRequest(final String email) {
        this.email = email;
    }
}
