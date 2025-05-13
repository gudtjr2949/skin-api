package com.personal.skin_api.common.oauth.naver.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthMemberInfoResponse {
    private String email;
    private String memberName;
    private String phone;

    @Builder
    private OAuthMemberInfoResponse(final String email,
                                    final String memberName,
                                    final String phone) {
        this.email = email;
        this.memberName = memberName;
        this.phone = phone;
    }
}