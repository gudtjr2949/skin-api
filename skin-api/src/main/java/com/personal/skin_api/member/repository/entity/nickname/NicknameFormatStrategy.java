package com.personal.skin_api.member.repository.entity.nickname;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.NicknameErrorCode;

import java.util.regex.Pattern;

import static com.personal.skin_api.common.exception.member.NicknameErrorCode.*;

class NicknameFormatStrategy implements NicknameValidationStrategy {
    private static final Pattern specialCharPattern = Pattern.compile("[!@?]");

    @Override
    public void validate(final String nickname) {
        if (specialCharPattern.matcher(nickname).find())
            throw new RestApiException(INVALID_NICKNAME_FORMAT);
    }
}
