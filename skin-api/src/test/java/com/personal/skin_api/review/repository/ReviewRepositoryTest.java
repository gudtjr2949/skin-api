package com.personal.skin_api.review.repository;

import com.personal.skin_api.JpaAbstractIntegrationTest;
import com.personal.skin_api.common.util.MerchantUidGenerator;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.payment.repository.entity.Payment;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.review.repository.entity.Review;

import com.personal.skin_api.review.repository.entity.ReviewStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class ReviewRepositoryTest extends JpaAbstractIntegrationTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @AfterEach
    void tearDown() {
        reviewRepository.deleteAllInBatch();
    }

    @Test
    void 후기를_생성한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);
        Review review = Review.builder()
                .member(member)
                .product(product)
                .order(order)
                .reviewContent("너무 좋은 스킨입니다. 잘 쓸께요")
                .build();

        // when
        Review savedReview = reviewRepository.save(review);

        // then
        assertThat(savedReview.getId()).isEqualTo(review.getId());
    }

    @Test
    void 후기_ID를_사용해_후기를_조회한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);
        Review review = Review.builder()
                .member(member)
                .product(product)
                .order(order)
                .reviewContent("너무 좋은 스킨입니다. 잘 쓸께요")
                .build();
        Review savedReview = reviewRepository.save(review);

        // when
        Optional<Review> byId = reviewRepository.findById(savedReview.getId());

        // then
        assertThat(byId).isPresent();
    }

    @Test
    void 스킨에_작성된_후기를_조회한다() {
        // given
        int reviewCnt = 5;
        Product product = createProduct(createGeneralMember());

        for (int i = 0 ; i < reviewCnt ; i++) {
            Member member = createGeneralMemberWithEmail("test" + i + "@naver.com");
            String orderUid = MerchantUidGenerator.generateMerchantUid();
            Order order = createOrder(member, product, orderUid);
            Payment payment = createPayment(order);
            createReview(member, product, order);
        }

        // when
        List<Review> byProduct = reviewRepository.findByProduct(product);

        // then
        assertThat(byProduct).hasSize(reviewCnt);
    }

    @Test
    void 로그인한_사용자가_작성한_후기_리스트를_조회한다() {
        // given
        Member skinBuyer = createGeneralMember();
        Member skinSeller = createGeneralMemberWithEmail("seller123@naver.com");

        int reviewCnt = 5;
        for (int i = 0 ; i < reviewCnt ; i++) {
            Product product = createProduct(skinSeller);
            String orderUid = MerchantUidGenerator.generateMerchantUid();
            Order order = createOrder(skinBuyer, product, orderUid);
            Payment payment = createPayment(order);
            createReview(skinBuyer, product, order);
        }

        // when
        List<Review> byMember = reviewRepository.findByMember(skinBuyer);

        // then
        assertThat(byMember).hasSize(reviewCnt);
    }

    @Test
    void 후기를_수정한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);
        Review review = createReview(member, product, order);
        String newReviewContent = "수정한 후기입니다. 수장한 후기입니다.";

        // when
        review.modifyReviewContent(newReviewContent);
        Optional<Review> byId = reviewRepository.findById(review.getId());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getReviewContent()).isEqualTo(newReviewContent);
    }

    @Test
    void 후기를_삭제한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);
        Review review = createReview(member, product, order);

        // when
        review.deleteReview();
        Optional<Review> byId = reviewRepository.findById(review.getId());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getReviewStatus()).isEqualTo(ReviewStatus.DELETED);
    }
}