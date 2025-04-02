package com.personal.skin_api.review.service;

import com.personal.skin_api.AbstractIntegrationTest;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.util.MerchantUidGenerator;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.payment.repository.entity.Payment;
import com.personal.skin_api.product.repository.entity.Product;

import com.personal.skin_api.review.repository.entity.Review;
import com.personal.skin_api.review.service.dto.request.ReviewCreateServiceRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ReviewServiceTest extends AbstractIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    void 후기를_생성한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);

        String reviewContent = "너무 좋은 스킨입니다! 잘 쓸께요!";

        ReviewCreateServiceRequest request = ReviewCreateServiceRequest.builder()
                .orderUid(orderUid)
                .email(member.getEmail())
                .reviewContent(reviewContent)
                .build();

        // when
        reviewService.createReview(request);
        List<Review> byProduct = reviewRepository.findByProduct(product);


        // then
        assertThat(byProduct).hasSize(1);
        assertThat(byProduct.get(0).getReviewContent()).isEqualTo(reviewContent);
    }

    @Test
    void 후기_작성을_시도한_사용자와_주문자의_정보가_다르다면_예외가_발생한다() {
        // given
        Member member = createGeneralMember();
        Member otherMember = createGeneralMemberWithEmail("other123@naver.com");
        Product product = createProduct(member);
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);

        String reviewContent = "너무 좋은 스킨입니다! 잘 쓸께요!";

        ReviewCreateServiceRequest request = ReviewCreateServiceRequest.builder()
                .orderUid(orderUid)
                .email(otherMember.getEmail())
                .reviewContent(reviewContent)
                .build();


        // when & then
        assertThatThrownBy(() -> reviewService.createReview(request))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 후기_작성을_시도한_주문의_결제가_아직_완료되지_않은_경우_예외가_발생한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);

        String reviewContent = "너무 좋은 스킨입니다! 잘 쓸께요!";

        ReviewCreateServiceRequest request = ReviewCreateServiceRequest.builder()
                .orderUid(orderUid)
                .email(member.getEmail())
                .reviewContent(reviewContent)
                .build();

        // when & then
        assertThatThrownBy(() -> reviewService.createReview(request))
                .isInstanceOf(RestApiException.class);
    }
}