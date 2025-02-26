package com.personal.skin_api.member.service.dto.request;

import lombok.Builder;
import lombok.Getter;

// TODO : 사용자 프로필 이미지 URL 추가 필요
@Getter
public class MemberModifyDetailServiceRequest {

    private final String email;
    private final String newMemberName;
    private final String newNickname;
    private final String newPhone;

    @Builder
    private MemberModifyDetailServiceRequest(final String email, final String newMemberName, String newNickname, String newPhone) {
        this.email = email;
        this.newMemberName = newMemberName;
        this.newNickname = newNickname;
        this.newPhone = newPhone;
    }
}
