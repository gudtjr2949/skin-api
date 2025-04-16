package com.personal.skin_api.member.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberReissueTokenResponse {
    private String newAccessToken;
    private String newRefreshUUID;

    @Builder
    private MemberReissueTokenResponse(final String newAccessToken,
                                       final String newRefreshUUID) {
        this.newAccessToken = newAccessToken;
        this.newRefreshUUID = newRefreshUUID;
    }
}
