package com.personal.skin_api.member.repository.entity.phone;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.PhoneErrorCode;

import static com.personal.skin_api.common.exception.member.PhoneErrorCode.*;

class PhoneBlankStrategy implements PhoneValidationStrategy {

    /**
     * 전화번호에 공백이 포함되어 있는지 검증한다.
     * @param phone 공백 포함 여부를 검증할 전화번호
     */
    @Override
    public void validate(final String phone) {
        if (phone.contains(" "))
            throw new RestApiException(PHONE_CANNOT_CONTAINS_BLANK);
    }
}
