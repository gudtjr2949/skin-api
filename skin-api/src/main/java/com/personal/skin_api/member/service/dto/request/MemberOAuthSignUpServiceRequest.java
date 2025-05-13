package com.personal.skin_api.member.service.dto.request;

import com.personal.skin_api.member.repository.entity.Member;
import lombok.Builder;
import lombok.Getter;

import static com.personal.skin_api.member.repository.entity.MemberRole.GENERAL;
import static com.personal.skin_api.member.repository.entity.MemberStatus.ACTIVE;

@Getter
public class MemberOAuthSignUpServiceRequest {
    private final String email;
    private final String memberName;
    private final String nickname;
    private final String phone;
    private final String provider;

    @Builder
    private MemberOAuthSignUpServiceRequest(final String email,
                                            final String memberName,
                                            final String nickname,
                                            final String phone,
                                            final String provider) {
        this.email = email;
        this.memberName = memberName;
        this.nickname = nickname;
        this.phone = phone;
        this.provider = provider;
    }

    public Member toEntity() {
        return Member.fromOAuth(email, memberName, phone, provider);
    }
}
