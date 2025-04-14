package com.personal.skin_api.review.service;

import com.personal.skin_api.AbstractIntegrationTest;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.util.MerchantUidGenerator;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.payment.repository.entity.Payment;
import com.personal.skin_api.product.repository.entity.Product;

import com.personal.skin_api.review.repository.entity.Review;
import com.personal.skin_api.review.repository.entity.ReviewStatus;
import com.personal.skin_api.review.service.dto.request.ReviewCreateServiceRequest;

import com.personal.skin_api.review.service.dto.request.ReviewDeleteServiceRequest;
import com.personal.skin_api.review.service.dto.request.ReviewFindListServiceRequest;
import com.personal.skin_api.review.service.dto.request.ReviewModifyServiceRequest;
import com.personal.skin_api.review.service.dto.response.ReviewListResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

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

    @Test
    void 이미_후기를_작성한_제품인_경우_예외가_발생한다() {
        // given
        Member seller = createGeneralMember();
        Product product = createProduct(seller);
        Member member = createGeneralMemberWithEmail("buyer123@naver.com");
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);
        Review review = createReview(member, product, order);
        ReviewCreateServiceRequest request = ReviewCreateServiceRequest.builder()
                .orderUid(orderUid)
                .email(member.getEmail())
                .reviewContent("리뷰내용입니다. 리뷰내용입니다.")
                .build();

        // when & then
        assertThatThrownBy(() -> reviewService.createReview(request))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 제품에_작성된_리뷰를_조회한다() {
        // given
        int reviewCnt = 5;
        Member seller = createGeneralMember();
        Product product = createProduct(seller);

        for (int i = 0 ; i < reviewCnt ; i++) {
            Member member = createGeneralMemberWithEmail("test" + i + "@naver.com");
            String orderUid = MerchantUidGenerator.generateMerchantUid();
            Order order = createOrder(member, product, orderUid);
            Payment payment = createPayment(order);
            createReview(member, product, order);
        }

        // when
        ReviewFindListServiceRequest request = ReviewFindListServiceRequest.builder()
                .productId(product.getId())
                .reviewId(0L)
                .build();

        ReviewListResponse reviewList = reviewService.findProductReviewList(request);

        // then
        assertThat(reviewList.getReviewDetailResponses()).hasSize(reviewCnt);
    }

    @Test
    void 후기를_수정한다() {
        // given
        Member seller = createGeneralMember();
        Product product = createProduct(seller);
        Member member = createGeneralMemberWithEmail("buyer123@naver.com");
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);
        Review review = createReview(member, product, order);
        String newReviewContent = "새로운 제품 후기입니다! 너무 좋네요!";

        ReviewModifyServiceRequest request = ReviewModifyServiceRequest.builder()
                .newReviewContent(newReviewContent)
                .email(member.getEmail())
                .reviewId(review.getId())
                .build();

        // when
        reviewService.modifyReview(request);
        Optional<Review> byId = reviewRepository.findById(review.getId());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getReviewContent()).isEqualTo(newReviewContent);
    }

    @Test
    void 수정하고자_하는_후기가_없는_경우_예외가_발생한다() {
        // given
        Member seller = createGeneralMember();
        Product product = createProduct(seller);
        Member member = createGeneralMemberWithEmail("buyer123@naver.com");
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);
        Review review = createReview(member, product, order);
        String newReviewContent = "새로운 제품 후기입니다! 너무 좋네요!";

        ReviewModifyServiceRequest request = ReviewModifyServiceRequest.builder()
                .newReviewContent(newReviewContent)
                .email(member.getEmail())
                .reviewId(review.getId()+1)
                .build();

        // when & then
        assertThatThrownBy(() -> reviewService.modifyReview(request))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 후기_작성자가_아닌_사용자가_후기_수정을_시도할_경우_예외가_발생한다() {
        // given
        Member seller = createGeneralMember();
        Product product = createProduct(seller);
        Member member = createGeneralMemberWithEmail("buyer123@naver.com");
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);
        Review review = createReview(member, product, order);
        String newReviewContent = "새로운 제품 후기입니다! 너무 좋네요!";

        Member otherMember = createGeneralMemberWithEmail("other123@naver.com");
        ReviewModifyServiceRequest request = ReviewModifyServiceRequest.builder()
                .newReviewContent(newReviewContent)
                .email(otherMember.getEmail())
                .reviewId(review.getId())
                .build();

        // when & then
        assertThatThrownBy(() -> reviewService.modifyReview(request))
                .isInstanceOf(RestApiException.class);
    }


    @Test
    void 후기를_삭제한다() {
        // given
        Member seller = createGeneralMember();
        Product product = createProduct(seller);
        Member member = createGeneralMemberWithEmail("buyer123@naver.com");
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);
        Review review = createReview(member, product, order);

        ReviewDeleteServiceRequest request = ReviewDeleteServiceRequest.builder()
                .email(member.getEmail())
                .reviewId(review.getId())
                .build();

        // when
        reviewService.deleteReview(request);
        Optional<Review> byId = reviewRepository.findById(review.getId());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getReviewStatus()).isEqualTo(ReviewStatus.DELETED);
    }

    // TODO : 다른 후기 ID를 '후기 ID+1' 로 표현할 경우
    //  만약 다른 테스트의 영향을 받는다면 테스트가 실패할 가능성 있음!
    @Test
    void 삭제하고자_하는_후기가_없는_경우_예외가_발생한다() {
        Member seller = createGeneralMember();
        Product product = createProduct(seller);
        Member member = createGeneralMemberWithEmail("buyer123@naver.com");
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);
        Review review = createReview(member, product, order);
        ReviewDeleteServiceRequest request = ReviewDeleteServiceRequest.builder()
                .email(member.getEmail())
                .reviewId(review.getId()+1)
                .build();

        // when & then
        assertThatThrownBy(() -> reviewService.deleteReview(request))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 후기_작성자가_아닌_사용자가_후기_삭제를_시도할_경우_예외가_발생한다() {
        Member seller = createGeneralMember();
        Product product = createProduct(seller);
        Member member = createGeneralMemberWithEmail("buyer123@naver.com");
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);
        Review review = createReview(member, product, order);
        Member otherMember = createGeneralMemberWithEmail("other123@naver.com");
        ReviewDeleteServiceRequest request = ReviewDeleteServiceRequest.builder()
                .email(otherMember.getEmail())
                .reviewId(review.getId())
                .build();

        // when & then
        assertThatThrownBy(() -> reviewService.deleteReview(request))
                .isInstanceOf(RestApiException.class);
    }
}