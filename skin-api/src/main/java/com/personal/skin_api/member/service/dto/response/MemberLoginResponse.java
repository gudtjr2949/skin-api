package com.personal.skin_api.member.service.dto.response;

import com.personal.skin_api.member.repository.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginResponse {
    private final String email;
    private final String memberName;
    private final String nickname;

    @Builder
    private MemberLoginResponse(final Member member) {
        this.email = member.getEmail();
        this.memberName = member.getMemberName();
        this.nickname = member.getNickname();
    }
}
