package com.personal.skin_api.product.service.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductListResponse {
    private final List<ProductResponse> productResponses;

    public ProductListResponse(final List<ProductResponse> productResponses) {
        this.productResponses = productResponses;
    }
}
