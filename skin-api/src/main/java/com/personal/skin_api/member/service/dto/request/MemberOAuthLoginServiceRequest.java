package com.personal.skin_api.member.service.dto.request;

import com.personal.skin_api.member.repository.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberOAuthLoginServiceRequest {
    private final String code;

    @Builder
    private MemberOAuthLoginServiceRequest(final String code) {
        this.code = code;
    }

    public Member toEntity(final String email,
                           final String memberName,
                           final String phone,
                           final String provider) {
        return Member.fromOAuth(email, memberName, phone, provider);
    }
}
