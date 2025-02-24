package com.personal.skin_api.member.repository.entity.email;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.EmailErrorCode;

class EmailNullStrategy implements EmailValidationStrategy {

    /**
     * 이메일이 null 인지 검증한다.
     * @param email null 여부를 검증할 이메일
     */
    @Override
    public void validate(String email) {
        if (email == null) throw new RestApiException(EmailErrorCode.EMAIL_CANNOT_BE_NULL);
    }
}
