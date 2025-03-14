package com.personal.skin_api.product.repository.entity.views;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class ProductViews {

    @Column(name = "PRODUCT_VIEWS")
    private int productViews;

    public ProductViews(final int productViews) {
        this.productViews = productViews;
    }

    public void increaseViews() {
        productViews++;
    }

    public int getProductViews() {
        return productViews;
    }
}
