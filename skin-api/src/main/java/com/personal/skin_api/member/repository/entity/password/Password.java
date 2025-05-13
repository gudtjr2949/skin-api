package com.personal.skin_api.member.repository.entity.password;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Password {

    @Column(name = "PASSWORD")
    private String password;

    private static final String OAUTH_PASSWORD_PLACEHOLDER = "OAUTH_MEMBER_NO_PASSWORD";

    private Password(final String password) {
        this.password = password;
    }

    public static Password fromRaw(final String rawPassword) {
        validate(rawPassword);
        return new Password(rawPassword);
    }

    public static Password fromEncoded(final String encodedPassword) {
        return new Password(encodedPassword);
    }

    public static String fromOAuth() {
         return OAUTH_PASSWORD_PLACEHOLDER;
    }

    private static void validate(final String password) {
         PasswordStrategyContext.runStrategy(password);
    }

    public String getPassword() {
        return password;
    }

    public Password setEncodedPassword(final String encodedPassword) {
         this.password = encodedPassword;
         return this;
    }

    public Password modifyPassword(final String newPassword) {
        validateNewPassword(newPassword);
        return new Password(newPassword);
    }

    private void validateNewPassword(final String newPassword) {
        ModifyPasswordStrategyContext.runStrategy(this.password, newPassword);
    }
}
