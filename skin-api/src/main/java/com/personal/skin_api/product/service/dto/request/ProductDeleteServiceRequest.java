package com.personal.skin_api.product.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductDeleteServiceRequest {
    private Long productId;
    private String email;

    @Builder
    private ProductDeleteServiceRequest(final Long productId, final String email) {
        this.productId = productId;
        this.email = email;
    }
}
