package com.personal.skin_api.member.repository.entity.member_name;

import java.util.List;

class MemberNameStrategyContext {
    private static final List<MemberNameValidationStrategy> memberNameValidationStrategies = List.of(
            new MemberNameNullStrategy(),
            new MemberNameBlankStrategy(),
            new MemberNameLengthStrategy(),
            new MemberNameFormatStrategy()
    );

    static void runStrategy(final String memberName) {
        memberNameValidationStrategies.stream().forEach(strategy -> strategy.validate(memberName));
    }
}
