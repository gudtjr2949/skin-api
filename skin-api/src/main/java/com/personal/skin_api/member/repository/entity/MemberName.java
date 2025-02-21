package com.personal.skin_api.member.repository.entity;

import com.personal.skin_api.common.exception.RestApiException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

import static com.personal.skin_api.common.exception.MemberErrorCode.*;

@Embeddable
@NoArgsConstructor
public class MemberName {
    private static final int NAME_MIN_LENGTH = 8, NAME_MAX_LENGTH = 20;
    private static final Pattern numberPattern = Pattern.compile("\\d"),
            specialCharPattern = Pattern.compile("[!@?]");

    @Column(name = "NAME")
    private String name;

    MemberName(final String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(final String name) {
        validateNameNull(name);
        validateNameContainsSpace(name);
        validateNameLength(name);
        validateNameFormat(name);
    }

    private void validateNameContainsSpace(String name) {
        if (name.contains(" "))
            throw new RestApiException(NAME_CANNOT_CONTAINS_SPACE);
    }

    private void validateNameFormat(final String name) {
        if (numberPattern.matcher(name).find() || specialCharPattern.matcher(name).find())
            throw new RestApiException(INVALID_NAME_TYPE);
    }

    private void validateNameLength(final String name) {
        if (name.length() < NAME_MIN_LENGTH || name.length() > NAME_MAX_LENGTH)
            throw new RestApiException(INVALID_NAME_LENGTH);
    }

    private void validateNameNull(final String name) {
        if (name == null)
            throw new RestApiException(NAME_CANNOT_BE_NULL);
    }
}
