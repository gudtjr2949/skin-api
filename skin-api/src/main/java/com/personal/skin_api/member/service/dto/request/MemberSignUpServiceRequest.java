package com.personal.skin_api.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberSignUpServiceRequest {
    private final String email;
    private final String password;
    private final String memberName;
    private final String nickname;
    private final String phone;

    @Builder
    private MemberSignUpServiceRequest(final String email, final String password, final String memberName, final String nickname, final String phone) {
        this.email = email;
        this.password = password;
        this.memberName = memberName;
        this.nickname = nickname;
        this.phone = phone;
    }
}
