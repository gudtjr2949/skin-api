package com.personal.skin_api.mail.service.dto.request;

import com.personal.skin_api.member.service.dto.request.MemberCertificationForFindPasswordServiceRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MailSendCertificationForPasswordServiceRequest {
    private final String email;
    private final String memberName;

    @Builder
    private MailSendCertificationForPasswordServiceRequest(String email, String memberName) {
        this.email = email;
        this.memberName = memberName;
    }

    public MemberCertificationForFindPasswordServiceRequest toMemberService() {
        return MemberCertificationForFindPasswordServiceRequest.builder()
                .email(email)
                .memberName(memberName)
                .build();
    }

}
