package com.personal.skin_api.member.controller.request;

import com.personal.skin_api.member.service.dto.request.MemberModifyDetailServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberModifyDetailRequest {
    private String memberName;
    private String nickname;
    private String phone;

    private MemberModifyDetailServiceRequest toService() {
        return MemberModifyDetailServiceRequest.builder()
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .build();
    }
}
