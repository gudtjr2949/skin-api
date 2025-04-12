package com.personal.skin_api.product.controller.dto.request;

import com.personal.skin_api.product.service.dto.request.ProductFindListServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductFindListRequest {
    private Long productId;
    private String sorter;
    private String keyword;
    private Long lastSortValue;

    public ProductFindListServiceRequest toService() {
        return ProductFindListServiceRequest.builder()
                .productId(productId)
                .sorter(sorter)
                .keyword(keyword)
                .lastSortValue(lastSortValue)
                .build();
    }
}
