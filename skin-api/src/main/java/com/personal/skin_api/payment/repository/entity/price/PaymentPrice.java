package com.personal.skin_api.payment.repository.entity.price;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class PaymentPrice {
    @Column(name = "PRICE")
    private Long price;

    public PaymentPrice(final Long price) {
        validate(price);
        this.price = price;
    }

    private void validate(final Long price) {
        PaymentPriceStrategyContext.runStrategy(price);
    }

    public Long getPrice() {
        return price;
    }
}
