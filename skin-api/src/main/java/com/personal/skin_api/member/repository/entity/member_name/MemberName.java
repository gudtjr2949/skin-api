package com.personal.skin_api.member.repository.entity.member_name;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@NoArgsConstructor
public class MemberName {

    @Column(name = "NAME")
    private String memberName;

    public MemberName(final String memberName) {
        validateName(memberName);
        this.memberName = memberName;
    }

    private void validateName(final String memberName) {
        MemberNameStrategyContext.runStrategy(memberName);
    }

    public String getMemberName() {
        return memberName;
    }
}
