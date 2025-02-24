package com.personal.skin_api.member.repository.entity.email;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.EmailErrorCode;

class EmailLengthStrategy implements EmailValidationStrategy {
    static final int EMAIL_MIN_LENGTH = 6, EMAIL_MAX_LENGTH = 320;

    /**
     * 이메일 길이가 EMAIL_MIN_LENGTH 이상, EMAIL_MAX_LENGTH 이하인지 검증한다.
     * @param email 길이 검증할 이메일
     */
    @Override
    public void validate(final String email) {
        if (email.length() < EMAIL_MIN_LENGTH || email.length() > EMAIL_MAX_LENGTH)
            throw new RestApiException(EmailErrorCode.INVALID_EMAIL_LENGTH);
    }
}
