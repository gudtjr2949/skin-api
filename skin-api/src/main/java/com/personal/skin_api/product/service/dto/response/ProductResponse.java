package com.personal.skin_api.product.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {
    private final Long productId;
    private final String productName;
    private final Long price;

    @Builder
    private ProductResponse(final Long productId, final String productName, final Long price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }
}
