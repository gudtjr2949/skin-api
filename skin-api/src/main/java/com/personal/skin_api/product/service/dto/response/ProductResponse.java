package com.personal.skin_api.product.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {
    private final Long productId;
    private final String productName;
    private final Long price;
    private final String thumbnailUrl;
    private final int orderCnt;
    private final int reviewCnt;

    @Builder
    private ProductResponse(
            final Long productId,
            final String productName,
            final Long price,
            final String thumbnailUrl,
            final int orderCnt,
            final int reviewCnt) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.orderCnt = orderCnt;
        this.reviewCnt = reviewCnt;
    }
}
