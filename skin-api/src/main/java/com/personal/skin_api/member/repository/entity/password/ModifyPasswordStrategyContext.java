package com.personal.skin_api.member.repository.entity.password;

import java.util.List;

class ModifyPasswordStrategyContext {
    private static final List<ModifyPasswordValidationStrategy> modifyPasswordValidationStrategies = List.of(
            new ModifyPasswordReuseStrategy()
    );

    static void runStrategy(final String beforePassword, final String newPassword) {
        modifyPasswordValidationStrategies.stream().forEach(strategy -> strategy.validate(beforePassword, newPassword));
    }
}
