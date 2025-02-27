package com.personal.skin_api.member.repository.entity.member_name;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.MemberNameErrorCode;

import static com.personal.skin_api.common.exception.member.MemberNameErrorCode.*;

class MemberNameNullStrategy implements MemberNameValidationStrategy {

    /**
     * 사용자 이름이 null 인지 검증한다.
     * @param memberName null 여부를 검증할 사용자 이름
     */
    @Override
    public void validate(final String memberName) {
        if (memberName == null)
            throw new RestApiException(NAME_CANNOT_BE_NULL);
    }
}
