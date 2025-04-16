package com.personal.skin_api.member.service.dto.response;

import com.personal.skin_api.member.repository.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginResponse {
    private final String email;
    private final String memberName;
    private final String nickname;
    private String accessToken;
    private String refreshUUID;

    @Builder
    private MemberLoginResponse(
            final Member member,
            final String accessToken,
            final String refreshUUID) {
        this.email = member.getEmail();
        this.memberName = member.getMemberName();
        this.nickname = member.getNickname();
        this.accessToken = accessToken;
        this.refreshUUID = refreshUUID;
    }

    public void removeAccessToken() {
        accessToken = "";
    }

    public void removeRefreshUUID() {
        refreshUUID = "";
    }
}
