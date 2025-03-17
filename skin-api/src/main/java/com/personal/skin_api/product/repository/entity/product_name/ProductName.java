package com.personal.skin_api.product.repository.entity.product_name;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class ProductName {

    @Column(name = "PRODUCT_NAME")
    private String productName;

    public ProductName(final String productName) {
        validate(productName);
        this.productName = productName;
    }

    private void validate(final String productName) {
        ProductNameStrategyContext.runStrategy(productName);
    }

    public String getProductName() {
        return productName;
    }
}
