package com.personal.skin_api.review.repository.entity;

import com.personal.skin_api.common.entity.BaseEntity;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.review.repository.entity.review_content.ReviewContent;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @OneToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Embedded
    private ReviewContent reviewContent;
}
