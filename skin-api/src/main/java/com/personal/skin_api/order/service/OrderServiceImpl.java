package com.personal.skin_api.order.service;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.MemberErrorCode;
import com.personal.skin_api.common.exception.payment.PaymentErrorCode;
import com.personal.skin_api.common.exception.product.ProductErrorCode;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.order.repository.OrderRepository;
import com.personal.skin_api.order.repository.PaymentRepository;
import com.personal.skin_api.order.repository.QOrderRepository;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.order.repository.entity.Payment;
import com.personal.skin_api.order.service.dto.request.OrderCreateServiceRequest;
import com.personal.skin_api.order.service.dto.response.OrderCreateResponse;
import com.personal.skin_api.order.service.dto.response.OrderDetailResponse;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final QOrderRepository qOrderRepository;
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final IamportClient iamportClient;

    @Override
    @Transactional
    public OrderCreateResponse createOrder(OrderCreateServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RestApiException(ProductErrorCode.PRODUCT_NOT_FOUND));

        Order order = Order.createBeforePayOrder(member, product);

        orderRepository.save(order);

        return OrderCreateResponse.builder()
                .memberName(member.getMemberName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .price(product.getPrice())
                .orderId(order.getId())
                .build();
    }

    public Payment findPayment(String impUid) throws IamportResponseException, IOException {
        IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse = iamportClient.paymentByImpUid(impUid);
        Long amount = (iamportResponse.getResponse().getAmount()).longValue();
        String payStatus = iamportResponse.getResponse().getStatus();
        String payMethod = iamportResponse.getResponse().getPayMethod();
        LocalDateTime paidAt = iamportResponse.getResponse().getPaidAt().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        if ("paid".equals(payStatus)) {
            Payment payment = Payment.builder()
                    .impUid(impUid)
                    .payMethod(payMethod)
                    .price(amount)
                    .paidAt(paidAt)
                    .build();

            return payment;
        }

        throw new RestApiException(PaymentErrorCode.CAN_NOT_FOUND_PAYMENT);
    }

    @Override
    public OrderDetailResponse findOrder(Long orderId) {

        return null;
    }
}
