package com.personal.skin_api.common.security;

interface JwtValidationStrategy {
    void validate(final String token);
}
