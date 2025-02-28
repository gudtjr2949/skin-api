package com.personal.skin_api.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberFindPasswordServiceRequest {
    private final String email;
    private final String memberName;
    private final String code;

    @Builder
    private MemberFindPasswordServiceRequest(final String email, final String memberName, final String code) {
        this.email = email;
        this.memberName = memberName;
        this.code = code;
    }
}
