package com.personal.skin_api.member.repository.entity.phone;

import com.personal.skin_api.common.exception.RestApiException;

import static com.personal.skin_api.common.exception.member.PhoneErrorCode.*;

class PhoneNullStrategy implements PhoneValidationStrategy {

    /**
     * 전화번호가 null 인지 검증한다.
     * @param phone null 여부를 검증할 전화번호
     */
    @Override
    public void validate(final String phone) {
        if (phone == null)
            throw new RestApiException(PHONE_CANNOT_BE_NULL);
    }
}
