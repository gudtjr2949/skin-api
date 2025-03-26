package com.personal.skin_api.product.repository.entity.price;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Price {

    @Column(name = "PRICE")
    private Long price;

    public Price(final Long price) {
        validate(price);
        this.price = price;
    }

    private void validate(final Long price) {
        PriceStrategyContext.runStrategy(price);
    }

    public Long getPrice() {
        return price;
    }
}
