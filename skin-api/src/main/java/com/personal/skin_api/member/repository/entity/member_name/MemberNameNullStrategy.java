package com.personal.skin_api.member.repository.entity.member_name;

import com.personal.skin_api.common.exception.RestApiException;

import static com.personal.skin_api.common.exception.MemberErrorCode.PASSWORD_CANNOT_BE_NULL;

class MemberNameNullStrategy implements MemberNameValidationStrategy {

    /**
     * 사용자 이름이 null 인지 판별한다.
     * @param memberName null 여부를 판별할 사용자 이름
     */
    @Override
    public void validate(String memberName) {
        if (memberName == null)
            throw new RestApiException(PASSWORD_CANNOT_BE_NULL);
    }
}
