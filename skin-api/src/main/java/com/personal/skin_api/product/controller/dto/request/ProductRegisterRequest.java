package com.personal.skin_api.product.controller.dto.request;

import com.personal.skin_api.product.service.dto.request.ProductRegisterServiceRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
public class ProductRegisterRequest {
    private String productName;
    private String productContent;
    private int price;
    private MultipartFile file;

    public ProductRegisterServiceRequest toService(String email) {
        return ProductRegisterServiceRequest.builder()
                .productName(productName)
                .productContent(productContent)
                .price(price)
                .file(file)
                .email(email)
                .build();
    }
}
