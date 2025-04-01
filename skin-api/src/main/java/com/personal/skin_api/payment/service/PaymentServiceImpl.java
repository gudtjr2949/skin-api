package com.personal.skin_api.payment.service;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.MemberErrorCode;
import com.personal.skin_api.common.exception.order.OrderErrorCode;
import com.personal.skin_api.common.exception.payment.PaymentErrorCode;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.order.repository.OrderRepository;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.payment.repository.PaymentRepository;
import com.personal.skin_api.payment.service.dto.request.PaymentCreateServiceRequest;
import com.personal.skin_api.payment.service.dto.request.PaymentFindServiceRequest;
import com.personal.skin_api.payment.service.dto.response.PaymentDetailResponse;
import com.personal.skin_api.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void createPayment(PaymentCreateServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        Order order = orderRepository.findByOrderUid(request.getOrderUid())
                .orElseThrow(() -> new RestApiException(OrderErrorCode.CAN_NOT_FOUND_ORDER));

        if (!order.getOrdererEmail().equals(member.getEmail()))
            throw new RestApiException(PaymentErrorCode.REQUESTER_ORDERER_MISMATCH);

        paymentRepository.save(request.toEntity(order));

        order.paidOrder();
    }

    @Override
    public PaymentDetailResponse findPayment(PaymentFindServiceRequest request) {
        return null;
    }
}
