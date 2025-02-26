package com.personal.skin_api.member.repository.entity.nickname;

import java.util.List;

class NicknameStrategyContext {
    private static final List<NicknameValidationStrategy> nicknameValidationStrategies = List.of(
            new NicknameNullStrategy(),
            new NicknameBlankStrategy(),
            new NicknameLengthStrategy(),
            new NicknameFormatStrategy()
    );

    static void runStrategy(String nickname) {
        nicknameValidationStrategies.stream().forEach(strategy -> strategy.validate(nickname));
    }
}
