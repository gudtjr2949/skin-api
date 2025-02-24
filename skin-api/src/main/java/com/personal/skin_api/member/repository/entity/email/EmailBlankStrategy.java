package com.personal.skin_api.member.repository.entity.email;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.EmailErrorCode;

class EmailBlankStrategy implements EmailValidationStrategy {

    /**
     * 이메일에 공백이 있는지 검증한다.
     * @param email 공백 포함 여부를 검증할 이메일
     */
    @Override
    public void validate(final String email) {
        if (email.contains(" ")) throw new RestApiException(EmailErrorCode.EMAIL_CANNOT_CONTAINS_BLANK);
    }
}
