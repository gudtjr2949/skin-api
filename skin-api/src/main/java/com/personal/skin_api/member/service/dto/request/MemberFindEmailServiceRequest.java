package com.personal.skin_api.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberFindEmailServiceRequest {
    private final String memberName;
    private final String phone;

    @Builder
    private MemberFindEmailServiceRequest(final String memberName, final String phone) {
        this.memberName = memberName;
        this.phone = phone;
    }
}
