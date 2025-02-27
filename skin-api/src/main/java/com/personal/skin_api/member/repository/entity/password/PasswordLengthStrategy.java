package com.personal.skin_api.member.repository.entity.password;

import com.personal.skin_api.common.exception.RestApiException;

import static com.personal.skin_api.common.exception.member.PasswordErrorCode.INVALID_PASSWORD_LENGTH;

class PasswordLengthStrategy implements PasswordValidationStrategy {
    private static final int PASSWORD_MIN_LENGTH = 8, PASSWORD_MAX_LENGTH = 20;

    /**
     * 비밀번호의 길이가 PASSWORD_MIN_LENGTH 이상 PASSWORD_MAX_LENGTH 이하인지 검증한다.
     * @param password 길이 검증할 비밀번호
     */
    @Override
    public void validate(final String password) {
        if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH)
            throw new RestApiException(INVALID_PASSWORD_LENGTH);
    }
}
