package com.personal.skin_api.member.service;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPasswordEncryption {

    private final PasswordEncoder passwordEncoder;

    public String encodePassword(final String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        return encodedPassword;
    }

    public void comparePassword(final String rawPassword,
                                final String encodedPassword) {
        boolean result = passwordEncoder.matches(rawPassword, encodedPassword);

        if (!result) throw new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND);
    }
}
