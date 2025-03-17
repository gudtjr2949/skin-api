package com.personal.skin_api.product.repository.entity.price;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Price {

    @Column(name = "PRICE")
    private int price;

    public Price(final int price) {
        validate(price);
        this.price = price;
    }

    private void validate(final int price) {
        PriceStrategyContext.runStrategy(price);
    }

    public int getPrice() {
        return price;
    }
}
