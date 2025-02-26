package com.personal.skin_api.member.repository.entity.phone;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@NoArgsConstructor
public class Phone {

    @Column(name = "PHONE")
    private String phone;

    public Phone(final String phone) {
        validate(phone);
        this.phone = phone;
    }

    private void validate(final String phone) {
        PhoneStrategyContext.runStrategy(phone);
    }

    public String getPhone() {
        return phone;
    }
}
