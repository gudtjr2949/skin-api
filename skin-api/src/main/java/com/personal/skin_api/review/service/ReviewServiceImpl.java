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
import com.personal.skin_api.review.repository.ReviewRepository;
import com.personal.skin_api.review.service.dto.request.ReviewCreateServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;


    @Override
    public void createReview(ReviewCreateServiceRequest request) {
        // Member 정보, Order 정보
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        Order order = orderRepository.findByOrderUid(request.getOrderUid())
                .orElseThrow(() -> new RestApiException(OrderErrorCode.CAN_NOT_FOUND_ORDER));

        Product product = productRepository.findById(order.getProductId())
                .orElseThrow(() -> new RestApiException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (!member.getEmail().equals(order.getOrdererEmail())) {
            throw new RestApiException(OrderErrorCode.INVALID_ORDER_MEMBER);
        }

        if (order.getPayment() == null) {
            throw new RestApiException(ReviewErrorCode.NOT_PAYMENT_ORDER);
        }


        reviewRepository.save(request.toEntity(member, product, order));
    }
}
