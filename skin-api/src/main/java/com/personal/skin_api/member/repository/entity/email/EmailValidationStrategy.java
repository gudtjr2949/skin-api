package com.personal.skin_api.member.repository.entity.email;

interface EmailValidationStrategy {
    void validate(final String email);
}