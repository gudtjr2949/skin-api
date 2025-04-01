package com.personal.skin_api;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.product.repository.entity.Product;

public class ProductFixture {
    /**
     * 제품 정보
     */
    private static String productName = "형석이의 스킨";
    private static String productContent = "아주 예쁜 스킨입니다!";
    private static String fileUrl = "s3://hyeongseok-skin/fileUrl";
    private static Long price = 10_000L;

    public static Product createProduct(Member member) {
        return Product.builder()
                .member(member)
                .productName(productName)
                .productContent(productContent)
                .fileUrl(fileUrl)
                .price(price)
                .build();
    }
}
