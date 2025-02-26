package com.personal.skin_api.member.service.dto.response;

import com.personal.skin_api.member.repository.entity.Member;
import lombok.Builder;
import lombok.Getter;

// TODO : 사용자 프로필 이미지 URL 추가 필요
@Getter
public class MemberDetailResponse {

    private final String email;
    private final String memberName;
    private final String nickname;
    private final String phone;

    @Builder
    private MemberDetailResponse(final Member member) {
        this.email = member.getEmail();
        this.memberName = member.getMemberName();
        this.nickname = member.getNickname();
        this.phone = member.getPhone();
    }
}
