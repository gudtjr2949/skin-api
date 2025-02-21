package com.personal.skin_api.member.repository.entity.password;

import com.personal.skin_api.common.exception.RestApiException;

import static com.personal.skin_api.common.exception.MemberErrorCode.NAME_CANNOT_CONTAINS_SPACE;

class PasswordSpaceStrategy implements PasswordValidationStrategy {

    /**
     * 비밀번호에 공백을 포함할 수 없다.
     * @param password 공백 포함 여부를 검증할 비밀번호
     */
    @Override
    public void validate(final String password) {
        if (password.contains(" "))
            throw new RestApiException(NAME_CANNOT_CONTAINS_SPACE);
    }
}
