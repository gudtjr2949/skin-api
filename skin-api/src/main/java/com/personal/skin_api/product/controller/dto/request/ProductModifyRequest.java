package com.personal.skin_api.product.controller.dto.request;

import com.personal.skin_api.product.service.dto.request.ProductModifyServiceRequest;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
public class ProductModifyRequest {
    private Long productId;
    private String productName;
    private String productContent;
    private int price;
    private MultipartFile file;

    public ProductModifyServiceRequest toService(String email) {
        return ProductModifyServiceRequest.builder()
                .productId(productId)
                .email(email)
                .newProductName(productName)
                .newProductContent(productContent)
                .newPrice(price)
                .newFile(file)
                .build();
    }

}
