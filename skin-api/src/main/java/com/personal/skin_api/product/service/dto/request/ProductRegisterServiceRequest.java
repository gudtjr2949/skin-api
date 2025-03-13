package com.personal.skin_api.product.service.dto.request;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.product.repository.entity.Product;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ProductRegisterServiceRequest {
    private final String productName;
    private final String productContent;
    private final int price;
    private final MultipartFile file;
    private final String email;

    @Builder
    private ProductRegisterServiceRequest(final String productName, final String productContent, final int price, final MultipartFile file, final String email) {
        this.productName = productName;
        this.productContent = productContent;
        this.price = price;
        this.file = file;
        this.email = email;
    }

    public Product toEntity(Member member, String fileUrl) {
        return Product.builder()
                .member(member)
                .productName(productName)
                .productContent(productContent)
                .price(price)
                .fileUrl(fileUrl)
                .build();
    }
}
