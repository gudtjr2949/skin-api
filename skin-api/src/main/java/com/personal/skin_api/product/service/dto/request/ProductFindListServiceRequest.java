package com.personal.skin_api.product.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductFindListServiceRequest {
    private final Long productId;
    private final String keyword;

    @Builder
    private ProductFindListServiceRequest(final Long productId, final String keyword) {
        this.productId = productId;
        this.keyword = keyword;
    }
}
