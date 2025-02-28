package com.personal.skin_api.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCertForFindPasswordServiceRequest {
    private final String email;
    private final String memberName;

    @Builder
    private MemberCertForFindPasswordServiceRequest(final String email, final String memberName) {
        this.email = email;
        this.memberName = memberName;
    }
}
