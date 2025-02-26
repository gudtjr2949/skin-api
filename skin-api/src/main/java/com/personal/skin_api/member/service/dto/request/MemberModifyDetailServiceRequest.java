package com.personal.skin_api.member.service.dto.request;

import com.personal.skin_api.member.controller.request.MemberModifyDetailRequest;
import lombok.Builder;
import lombok.Getter;

// TODO : 사용자 프로필 이미지 URL 추가 필요
@Getter
public class MemberModifyDetailServiceRequest {

    private final String email;
    private final String memberName;
    private final String nickname;
    private final String phone;

    @Builder
    private MemberModifyDetailServiceRequest(final String email, final String memberName, String nickname, String phone) {
        this.email = email;
        this.memberName = memberName;
        this.nickname = nickname;
        this.phone = phone;
    }
}
