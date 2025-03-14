package com.personal.skin_api.product.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductFindListServiceRequest {
    private final Long productId;
    private final String sorter;
    private final String keyword;

    @Builder
    private ProductFindListServiceRequest(final Long productId, final String sorter, final String keyword) {
        this.productId = productId;
        this.sorter = sorter;
        this.keyword = keyword;
    }
}
