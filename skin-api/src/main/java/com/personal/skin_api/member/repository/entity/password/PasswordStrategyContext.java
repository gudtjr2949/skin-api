package com.personal.skin_api.member.repository.entity.password;

import java.util.List;

class PasswordStrategyContext {

    private static final List<PasswordValidationStrategy> passwordValidationStrategies = List.of(
            new PasswordNullStrategy(),
            new PasswordBlankStrategy(),
            new PasswordLengthStrategy(),
            new PasswordFormatStrategy()
    );

    static void runStrategy(final String password) {
        passwordValidationStrategies.stream().forEach(strategy -> strategy.validate(password));
    }
}
