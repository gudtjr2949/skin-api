package com.personal.skin_api.member.service.dto.request;

import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.member.repository.entity.member_name.MemberName;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCertForFindPasswordServiceRequest {
    private final Email email;
    private final MemberName memberName;

    @Builder
    private MemberCertForFindPasswordServiceRequest(final String email, final String memberName) {
        this.email = new Email(email);
        this.memberName = new MemberName(memberName);
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getMemberName() {
        return memberName.getMemberName();
    }

}
