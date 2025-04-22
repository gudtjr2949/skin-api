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
    private final String blogUrl;
    private final Long price;
    private final MultipartFile file;
    private final MultipartFile thumbnail;
    private final String email;

    @Builder
    private ProductRegisterServiceRequest(final String productName,
                                          final String productContent,
                                          final String blogUrl,
                                          final Long price,
                                          final MultipartFile file,
                                          final MultipartFile thumbnail,
                                          final String email) {
        this.productName = productName;
        this.productContent = productContent;
        this.blogUrl = blogUrl;
        this.price = price;
        this.file = file;
        this.thumbnail = thumbnail;
        this.email = email;
    }

    public Product toEntity(Member member, String fileUrl, String thumbnailUrl) {
        return Product.builder()
                .member(member)
                .productName(productName)
                .productContent(productContent)
                .blogUrl(blogUrl)
                .price(price)
                .fileUrl(fileUrl)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
