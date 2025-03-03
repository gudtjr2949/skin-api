package com.personal.skin_api.common.security;

import com.personal.skin_api.common.exception.JwtErrorCode;
import com.personal.skin_api.common.exception.RestApiException;

public class JwtNullStrategy implements JwtValidationStrategy {

    /**
     * 토큰이 null 인지 검증한다.
     * @param token null 여부를 검증할 토큰
     */
    @Override
    public void validate(final String token) {
        if (token == null) throw new RestApiException(JwtErrorCode.JWT_CANNOT_BE_NULL);
    }
}
