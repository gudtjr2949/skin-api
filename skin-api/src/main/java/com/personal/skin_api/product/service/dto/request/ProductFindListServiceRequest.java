package com.personal.skin_api.product.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductFindListServiceRequest {
    private final Long productId;
    private final String sorter;
    private final String keyword;
    private final Long lastSortValue;

    @Builder
    private ProductFindListServiceRequest(
            final Long productId,
            final String sorter,
            final String keyword,
            final Long lastSortValue) {
        this.productId = productId;
        this.sorter = sorter;
        this.keyword = keyword;
        this.lastSortValue = lastSortValue;
    }

    public String toCacheKey() {
        return String.join(":",
                String.valueOf(sorter),
                String.valueOf(keyword),
                String.valueOf(productId),
                String.valueOf(lastSortValue)
        );
    }
}
