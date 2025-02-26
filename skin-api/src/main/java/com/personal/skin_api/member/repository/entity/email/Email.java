package com.personal.skin_api.member.repository.entity.email;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@NoArgsConstructor
public class Email {

    private static EmailStrategyContext emailStrategyContext = new EmailStrategyContext();

    @Column(name = "EMAIL")
    private String email;

    public Email(final String email) {
        validate(email);
        this.email = email;
    }

    private void validate(final String email) {
        emailStrategyContext.runStrategy(email);
    }

    public String getEmail() {
        return email;
    }
}
