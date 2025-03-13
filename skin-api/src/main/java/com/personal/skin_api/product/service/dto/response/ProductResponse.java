package com.personal.skin_api.product.service.dto.response;

import lombok.Builder;

public class ProductResponse {
    private final Long productId;
    private final String productName;
    private final int price;

    @Builder
    private ProductResponse(final Long productId, final String productName, final int price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }
}
