package com.personal.skin_api.review.service.dto.request;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.review.repository.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewCreateServiceRequest {

    private String orderUid;
    private String email;
    private String reviewContent;
    private int star;

    @Builder
    private ReviewCreateServiceRequest(final String orderUid,
                                       final String email,
                                       final String reviewContent,
                                       final int star) {
        this.orderUid = orderUid;
        this.email = email;
        this.reviewContent = reviewContent;
        this.star = star;
    }

    public Review toEntity(Member member, Product product, Order order) {
        return Review.builder()
                .member(member)
                .product(product)
                .order(order)
                .reviewContent(reviewContent)
                .star(star)
                .build();
    }
}
