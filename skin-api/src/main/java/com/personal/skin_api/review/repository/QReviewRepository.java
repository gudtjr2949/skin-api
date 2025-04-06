package com.personal.skin_api.review.repository;

import com.personal.skin_api.review.repository.entity.QReview;
import com.personal.skin_api.review.repository.entity.Review;
import com.personal.skin_api.review.repository.entity.ReviewStatus;
import com.querydsl.core.BooleanBuilder;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QReviewRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    public static final int REVIEWS_PAGE_SIZE = 5;

    public QReviewRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Review> findProductReviews(Long productId, Long reviewId) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(QReview.review.reviewStatus.eq(ReviewStatus.ACTIVE));
        builder.and(QReview.review.product.id.eq(productId));

        if (reviewId > 0) {
            builder.and(QReview.review.id.lt(reviewId));
        }

        List<Review> findReviews = queryFactory
                .selectFrom(QReview.review)
                .where(builder)
                .orderBy(QReview.review.id.desc())
                .limit(REVIEWS_PAGE_SIZE)
                .fetch();

        return findReviews;
    }



}
