package com.personal.skin_api.member.repository.entity.nickname;

import com.personal.skin_api.common.exception.RestApiException;

import static com.personal.skin_api.common.exception.member.NicknameErrorCode.*;

public class NicknameNullStrategy implements NicknameValidationStrategy {

    /**
     * 닉네임이 null 인지 검증한다.
     * @param nickname null 여부를 검증할 닉네임
     */
    @Override
    public void validate(final String nickname) {
        if (nickname == null)
            throw new RestApiException(NICKNAME_CANNOT_BE_NULL);
    }
}
