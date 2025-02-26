package com.personal.skin_api.member.repository.entity.email;

import java.util.List;

class EmailStrategyContext {
    private static final List<EmailValidationStrategy> emailValidationStrategies = List.of(
            new EmailNullStrategy(),
            new EmailBlankStrategy(),
            new EmailLengthStrategy(),
            new EmailFormatStrategy()
    );

    void runStrategy(final String email) {
        emailValidationStrategies.stream().forEach(strategy -> strategy.validate(email));
    }
}
