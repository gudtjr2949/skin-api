package com.personal.skin_api.member.repository.entity.password;

import com.personal.skin_api.common.exception.RestApiException;

import static com.personal.skin_api.common.exception.member.PasswordErrorCode.PASSWORD_CANNOT_BE_NULL;

class PasswordNullStrategy implements PasswordValidationStrategy {

    /**
     * 비밀번호가 null 인지 검증한다.
     * @param password null 여부를 검증할 비밀번호
     */
    @Override
    public void validate(final String password) {
        if (password == null)
            throw new RestApiException(PASSWORD_CANNOT_BE_NULL);
    }
}
