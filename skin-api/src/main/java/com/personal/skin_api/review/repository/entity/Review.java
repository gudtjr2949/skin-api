package com.personal.skin_api.review.repository.entity;

import com.personal.skin_api.common.entity.BaseEntity;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.review.repository.entity.review_content.ReviewContent;
import jakarta.persistence.*;
import lombok.Builder;
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

    @Column(name = "STAR")
    private int star;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "REVIEW_STATUS")
    private ReviewStatus reviewStatus;

    @Builder
    private Review(final Product product,
                   final Order order,
                   final Member member,
                   final String reviewContent,
                   final int star) {
        this.product = product;
        this.order = order;
        this.member = member;
        this.reviewContent = new ReviewContent(reviewContent);
        this.reviewStatus = ReviewStatus.ACTIVE;
        this.star = star;
    }

    public void modifyReviewContent(final String newReviewContent) {
        this.reviewContent = new ReviewContent(newReviewContent);
    }

    public void modifyReviewStar(final int newStar) {
        this.star = newStar;
    }

    public void deleteReview() {
        this.reviewStatus = ReviewStatus.DELETED;
    }

    public Long getId() {
        return id;
    }

    public String getReviewContent() {
        return reviewContent.getReviewContent();
    }

    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    public String getReviewerNickname() {
        return member.getNickname();
    }

    public String getReviewerEmail() {
        return member.getEmail();
    }

    public Long getProductId() {
        return product.getId();
    }

    public String getProductName() {
        return product.getProductName();
    }

    public int getStar() {
        return star;
    }
}
