package com.personal.skin_api.product.service.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ProductModifyServiceRequest {
    private final Long productId;
    private final String email;
    private final String newProductName;
    private final String newProductContent;
    private final Long newPrice;
    private final MultipartFile newFile;

    @Builder
    private ProductModifyServiceRequest(final Long productId,
                                        final String email,
                                        final String newProductName,
                                        final String newProductContent,
                                        final Long newPrice,
                                        final MultipartFile newFile) {
        this.productId = productId;
        this.email = email;
        this.newProductName = newProductName;
        this.newProductContent = newProductContent;
        this.newPrice = newPrice;
        this.newFile = newFile;
    }
}
