package com.personal.skin_api.member.repository.entity.password;

import com.personal.skin_api.common.exception.RestApiException;

import static com.personal.skin_api.common.exception.MemberErrorCode.PASSWORD_CANNOT_BE_NULL;

class NullValidationStrategy implements PasswordValidationStrategy {

    /**
     * 비밀번호는 null 일 수 없다.
     * @param password : null 을 검증할 비밀번호
     */
    @Override
    public void validate(String password) {
        if (password == null)
            throw new RestApiException(PASSWORD_CANNOT_BE_NULL);
    }
}
