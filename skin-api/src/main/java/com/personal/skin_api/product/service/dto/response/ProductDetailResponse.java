package com.personal.skin_api.product.service.dto.response;

import com.personal.skin_api.product.repository.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductDetailResponse {
    private final Long productId;
    private final String productName;
    private final String productContent;
    private final Long price;
    private final String registrantNickname;
    private final int productViews;

    @Builder
    private ProductDetailResponse(final Long productId,
                                  final String productName,
                                  final String productContent,
                                  final Long price,
                                  final String registrantNickname,
                                  final int productViews) {
        this.productId = productId;
        this.productName = productName;
        this.productContent = productContent;
        this.price = price;
        this.registrantNickname = registrantNickname;
        this.productViews = productViews;
    }
}
