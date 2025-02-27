package com.personal.skin_api.member.repository.entity.nickname;

import com.personal.skin_api.common.exception.RestApiException;

import static com.personal.skin_api.common.exception.member.NicknameErrorCode.*;

class NicknameBlankStrategy implements NicknameValidationStrategy {

    /**
     * 닉네임에 공백이 포함되어 있는지 검증한다.
     * @param nickname 공백 포함 여부를 검증할 닉네임
     */
    @Override
    public void validate(final String nickname) {
        if (nickname.contains(" "))
            throw new RestApiException(NICKNAME_CANNOT_CONTAINS_BLANK);
    }
}
