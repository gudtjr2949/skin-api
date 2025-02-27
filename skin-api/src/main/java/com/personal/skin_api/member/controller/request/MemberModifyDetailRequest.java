package com.personal.skin_api.member.controller.request;

import com.personal.skin_api.member.service.dto.request.MemberModifyDetailServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberModifyDetailRequest {
    private String newMemberName;
    private String newNickname;
    private String newPhone;

    public MemberModifyDetailServiceRequest toService() {
        return MemberModifyDetailServiceRequest.builder()
                .newMemberName(newMemberName)
                .newNickname(newNickname)
                .newPhone(newPhone)
                .build();
    }
}
