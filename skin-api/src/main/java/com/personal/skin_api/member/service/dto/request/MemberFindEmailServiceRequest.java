package com.personal.skin_api.member.service.dto.request;

import com.personal.skin_api.member.repository.entity.member_name.MemberName;
import com.personal.skin_api.member.repository.entity.phone.Phone;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberFindEmailServiceRequest {
    private final MemberName memberName;
    private final Phone phone;
    private final String authNumber;

    @Builder
    private MemberFindEmailServiceRequest(final String memberName, final String phone, final String authNumber) {
        this.memberName = new MemberName(memberName);
        this.phone = new Phone(phone);
        this.authNumber = authNumber;
    }
}
