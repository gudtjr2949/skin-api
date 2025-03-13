package com.personal.skin_api.product.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductFindMyListServiceRequest {
    private final Long productId;
    private final String email;

    @Builder
    private ProductFindMyListServiceRequest(final Long productId, final String email) {
        this.productId = productId;
        this.email = email;
    }
}
