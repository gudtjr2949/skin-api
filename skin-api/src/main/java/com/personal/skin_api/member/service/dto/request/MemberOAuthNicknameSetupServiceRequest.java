package com.personal.skin_api.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberOAuthNicknameSetupServiceRequest {

    private String email;
    private String nickname;

    @Builder
    private MemberOAuthNicknameSetupServiceRequest(final String email,
                                                   final String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
