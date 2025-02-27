package com.personal.skin_api.member.repository.entity.phone;

import java.util.List;

class PhoneStrategyContext {
    private static final List<PhoneValidationStrategy> phoneValidationStrategies = List.of(
            new PhoneNullStrategy(),
            new PhoneBlankStrategy(),
            new PhoneLengthStrategy(),
            new PhoneFormatStrategy()
    );

    static void runStrategy(final String phone) {
        phoneValidationStrategies.stream().forEach(strategy -> strategy.validate(phone));
    }
}
