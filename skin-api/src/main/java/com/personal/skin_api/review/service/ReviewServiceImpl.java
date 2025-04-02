package com.personal.skin_api.review.service;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.MemberErrorCode;
import com.personal.skin_api.common.exception.order.OrderErrorCode;
import com.personal.skin_api.common.exception.product.ProductErrorCode;
import com.personal.skin_api.common.exception.review.ReviewErrorCode;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.order.repository.OrderRepository;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.product.repository.entity.ProductStatus;
import com.personal.skin_api.review.repository.QReviewRepository;
import com.personal.skin_api.review.repository.ReviewRepository;
import com.personal.skin_api.review.repository.entity.Review;
import com.personal.skin_api.review.repository.entity.ReviewStatus;
import com.personal.skin_api.review.service.dto.request.ReviewCreateServiceRequest;
import com.personal.skin_api.review.service.dto.request.ReviewDeleteServiceRequest;
import com.personal.skin_api.review.service.dto.request.ReviewFindListServiceRequest;
import com.personal.skin_api.review.service.dto.request.ReviewModifyServiceRequest;
import com.personal.skin_api.review.service.dto.response.ReviewDetailResponse;
import com.personal.skin_api.review.service.dto.response.ReviewListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.personal.skin_api.review.repository.entity.ReviewStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final QReviewRepository qReviewRepository;


    @Override
    @Transactional
    public void createReview(ReviewCreateServiceRequest request) {
        // Member 정보, Order 정보
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        Order order = orderRepository.findByOrderUid(request.getOrderUid())
                .orElseThrow(() -> new RestApiException(OrderErrorCode.CAN_NOT_FOUND_ORDER));

        Product product = productRepository.findByIdAndProductStatus(order.getProductId(), ProductStatus.ACTIVE)
                .orElseThrow(() -> new RestApiException(ProductErrorCode.PRODUCT_NOT_FOUND));


        if (!member.getEmail().equals(order.getOrdererEmail())) {
            throw new RestApiException(OrderErrorCode.INVALID_ORDER_MEMBER);
        }

        if (order.getPayment() == null) {
            throw new RestApiException(ReviewErrorCode.NOT_PAYMENT_ORDER);
        }

        Optional<Review> findReview = reviewRepository.findByMemberAndProductAndOrder(member, product, order);

        if (findReview.isPresent())
            throw new RestApiException(ReviewErrorCode.DUPLICATE_REVIEW);

        reviewRepository.save(request.toEntity(member, product, order));
    }



    @Override
    public ReviewListResponse findReviewList(ReviewFindListServiceRequest request) {
        Product product = productRepository.findByIdAndProductStatus(request.getProductId(), ProductStatus.ACTIVE)
                .orElseThrow(() -> new RestApiException(ProductErrorCode.PRODUCT_NOT_FOUND));

        List<Review> productReviews = qReviewRepository.findProductReviews(product.getId(), request.getReviewId());

        List<ReviewDetailResponse> reviewDetailResponses = productReviews.stream()
                .map(review -> ReviewDetailResponse.builder()
                        .reviewId(review.getId())
                        .reviewContent(review.getReviewContent())
                        .nickname(review.getReviewerNickname())
                        .createdAt(review.getCreatedAt())
                        .build())
                .toList();

        return new ReviewListResponse(reviewDetailResponses);
    }

    @Override
    public int findProductReviewCount(Long productId) {
        Product product = productRepository.findByIdAndProductStatus(productId, ProductStatus.ACTIVE)
                .orElseThrow(() -> new RestApiException(ProductErrorCode.PRODUCT_NOT_FOUND));

        List<Review> byProduct = reviewRepository.findByProduct(product);

        return byProduct.size();
    }

    @Override
    @Transactional
    public void modifyReview(ReviewModifyServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        Review review = reviewRepository.findByIdAndReviewStatus(request.getReviewId(), ACTIVE)
                .orElseThrow(() -> new RestApiException(ReviewErrorCode.CAN_NOT_FOUND_REVIEW));

        checkReviewerPermission(member, review);

        review.modifyReviewStatus(request.getNewReviewContent());
    }

    @Override
    @Transactional
    public void deleteReview(ReviewDeleteServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        Review review = reviewRepository.findByIdAndReviewStatus(request.getReviewId(), ACTIVE)
                .orElseThrow(() -> new RestApiException(ReviewErrorCode.CAN_NOT_FOUND_REVIEW));

        checkReviewerPermission(member, review);

        review.deleteReview();
    }

    private void checkReviewerPermission(Member member, Review review) {
        if (!member.getEmail().equals(review.getReviewerEmail()))
            throw new RestApiException(ReviewErrorCode.CAN_NOT_MODIFY_REVIEW);
    }
}
