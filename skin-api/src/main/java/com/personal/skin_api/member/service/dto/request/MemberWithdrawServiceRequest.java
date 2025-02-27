package com.personal.skin_api.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberWithdrawServiceRequest {
    private final String email;

    @Builder
    private MemberWithdrawServiceRequest(final String email) {
        this.email = email;
    }
}
