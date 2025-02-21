package com.personal.skin_api.member.repository.entity.nickname;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.NicknameErrorCode;

import static com.personal.skin_api.common.exception.member.NicknameErrorCode.*;

public class NicknameLengthStrategy implements NicknameValidationStrategy {
    private static final int NICKNAME_MIN_LENGTH = 3, NICKNAME_MAX_LENGTH = 8;

    /**
     * 닉네임 길이가 NICKNAME_MIN_LENGTH 이상, NICKNAME_MAX_LENGTH 이하인지 검증한다.
     * @param nickname 길이 검증할 닉네임
     */
    @Override
    public void validate(String nickname) {
        if (nickname.length() < NICKNAME_MIN_LENGTH || nickname.length() > NICKNAME_MAX_LENGTH)
            throw new RestApiException(INVALID_NICKNAME_LENGTH);
    }
}
