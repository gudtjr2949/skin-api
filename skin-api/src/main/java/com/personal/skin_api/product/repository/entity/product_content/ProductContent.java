package com.personal.skin_api.product.repository.entity.product_content;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.RequiredArgsConstructor;

@Embeddable
@RequiredArgsConstructor
public class ProductContent {

    @Column(name = "PRODUCT_CONTENT")
    private String productContent;


    public ProductContent(final String productContent) {
        validate(productContent);
        this.productContent = productContent;
    }

    private void validate(final String productContent) {
        ProductContentStrategyContext.runStrategy(productContent);
    }

    public String getProductContent() {
        return productContent;
    }
}
