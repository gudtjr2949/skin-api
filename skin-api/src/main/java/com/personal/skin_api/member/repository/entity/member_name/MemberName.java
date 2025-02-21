package com.personal.skin_api.member.repository.entity.member_name;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@NoArgsConstructor
public class MemberName {

    private static final List<MemberNameValidationStrategy> memberNameValidationStrategies = List.of(
            new MemberNameNullStrategy(),
            new MemberNameBlankStrategy(),
            new MemberNameLengthStrategy(),
            new MemberNameFormatStrategy()
    );

    @Column(name = "NAME")
    private String memberName;

    public MemberName(final String memberName) {
        validateName(memberName);
        this.memberName = memberName;
    }

    private void validateName(final String memberName) {
        memberNameValidationStrategies.stream().forEach(strategy -> strategy.validate(memberName));
    }

    public String getMemberName() {
        return memberName;
    }
}
