package com.personal.skin_api.review.repository;

import com.personal.skin_api.AbstractIntegrationTest;
import com.personal.skin_api.common.util.MerchantUidGenerator;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.payment.repository.entity.Payment;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.review.repository.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.personal.skin_api.review.repository.QReviewRepository.*;
import static org.assertj.core.api.Assertions.assertThat;

class QReviewRepositoryTest extends AbstractIntegrationTest {


    @Autowired
    private QReviewRepository qReviewRepository;

    @Test
    void 스킨에_작성된_후기_첫_장을_조회한다() {
        // given
        int reviewCnt = 3;
        Product product = createProduct(createGeneralMember());

        for (int i = 0 ; i < reviewCnt ; i++) {
            Member member = createGeneralMemberWithEmail("test" + i + "@naver.com");
            String orderUid = MerchantUidGenerator.generateMerchantUid();
            Order order = createOrder(member, product, orderUid);
            Payment payment = createPayment(order);
            createReview(member, product, order);
        }

        // when
        List<Review> productReviews = qReviewRepository.findProductReviews(product.getId(), 0L);


        // then
        assertThat(productReviews).hasSize(reviewCnt);
    }

    @Test
    void 스킨에_작성된_후기_마지막_장을_조회한다() {
        // given
        int reviewCnt = 5;
        Product product = createProduct(createGeneralMember());

        Review lastReview = null;
        Review fistReview = null;

        for (int i = 0 ; i < reviewCnt ; i++) {
            Member member = createGeneralMemberWithEmail("test" + i + "@naver.com");
            String orderUid = MerchantUidGenerator.generateMerchantUid();
            Order order = createOrder(member, product, orderUid);
            Payment payment = createPayment(order);
            if (i == 0) {
                lastReview = createReview(member, product, order);
            } else if (i == reviewCnt-1) {
                fistReview = createReview(member, product, order);
            } else {
                createReview(member, product, order);
            }
        }

        Review recentView = null;

        for (int i = 0 ; i < reviewCnt ; i++) {
            Member member = createGeneralMemberWithEmail("test" + i + "@naver.com");
            String orderUid = MerchantUidGenerator.generateMerchantUid();
            Order order = createOrder(member, product, orderUid);
            Payment payment = createPayment(order);
            if (i == 0) {
                recentView = createReview(member, product, order);
            } else {
                createReview(member, product, order);
            }
        }


        // when
        List<Review> productReviews = qReviewRepository.findProductReviews(product.getId(), recentView.getId());

        // then
        assertThat(productReviews).hasSize(REVIEWS_PAGE_SIZE);
        assertThat(productReviews.get(0).getId()).isEqualTo(fistReview.getId());
        assertThat(productReviews.get(productReviews.size()-1).getId()).isEqualTo(lastReview.getId());
    }
}