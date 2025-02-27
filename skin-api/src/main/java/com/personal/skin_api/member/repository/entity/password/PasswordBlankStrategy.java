package com.personal.skin_api.member.repository.entity.password;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.PasswordErrorCode;

import static com.personal.skin_api.common.exception.member.PasswordErrorCode.*;

class PasswordBlankStrategy implements PasswordValidationStrategy {

    /**
     * 비밀번호에 공백이 포함되어 있는지 검증한다.
     * @param password 공백 포함 여부를 검증할 비밀번호
     */
    @Override
    public void validate(final String password) {
        if (password.contains(" "))
            throw new RestApiException(PASSWORD_CANNOT_CONTAINS_BLANK);
    }
}
