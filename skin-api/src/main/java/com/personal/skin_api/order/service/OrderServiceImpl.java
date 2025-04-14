package com.personal.skin_api.order.service;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.MemberErrorCode;
import com.personal.skin_api.common.exception.order.OrderErrorCode;
import com.personal.skin_api.common.exception.product.ProductErrorCode;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.order.repository.OrderRepository;
import com.personal.skin_api.order.repository.entity.OrderStatus;
import com.personal.skin_api.order.service.dto.response.OrderLineResponse;
import com.personal.skin_api.payment.repository.PaymentRepository;
import com.personal.skin_api.order.repository.QOrderRepository;
import com.personal.skin_api.order.repository.entity.Order;

import com.personal.skin_api.order.service.dto.request.OrderCreateBeforePaidServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderCreateTableServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderDetailServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderListServiceRequest;
import com.personal.skin_api.order.service.dto.response.OrderCreateTableResponse;
import com.personal.skin_api.order.service.dto.response.OrderDetailResponse;
import com.personal.skin_api.order.service.dto.response.OrderListResponse;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.personal.skin_api.common.util.MerchantUidGenerator.generateMerchantUid;

@Aspect
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final QOrderRepository qOrderRepository;
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderCreateTableResponse createOrderTable(OrderCreateTableServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RestApiException(ProductErrorCode.PRODUCT_NOT_FOUND));

        return OrderCreateTableResponse.builder()
                .memberName(member.getMemberName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .price(product.getPrice())
                .build();
    }

    @Override
    @Transactional
    public String createBeforePaidOrder(OrderCreateBeforePaidServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RestApiException(ProductErrorCode.PRODUCT_NOT_FOUND));

        String orderUid = generateMerchantUid();

        Order order = Order.createBeforePayOrder(member, product, orderUid);

        orderRepository.save(order);

        return orderUid;
    }

    @Override
    public OrderDetailResponse findOrder(OrderDetailServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        Order order = orderRepository.findByOrderUidAndMember(request.getOrderUid(), member)
                .orElseThrow(() -> new RestApiException(OrderErrorCode.CAN_NOT_FOUND_ORDER));

        OrderDetailResponse response = new OrderDetailResponse();

        if (order.getPayment() == null) {
            response = createOrderDetailResponseWhenNoPayment(order, member.getMemberName());
        } else {
            response = createOrderDetailResponse(order, member.getMemberName());
        }

        return response;
    }

    private OrderDetailResponse createOrderDetailResponse(Order order, String memberName) {
        return OrderDetailResponse.builder()
                .orderUid(order.getOrderUid())
                .memberName(memberName)
                .productName(order.getProductName())
                .price(order.getPaymentPrice())
                .payMethod(order.getPayMethod())
                .paidAt(order.getPaidAt())
                .createdAt(order.getCreatedAt())
                .build();
    }

    private OrderDetailResponse createOrderDetailResponseWhenNoPayment(Order order, String memberName) {
        return OrderDetailResponse.builder()
                .orderUid(order.getOrderUid())
                .memberName(memberName)
                .productName(order.getProductName())
                .createdAt(order.getCreatedAt())
                .build();
    }

    @Override
    public OrderListResponse findMyOrderList(OrderListServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        List<Order> orderList = qOrderRepository.findMyOrderList(request.getOrderId(), member, request.getKeyword(), request.getYear());

        List<OrderLineResponse> orderResponses = orderList.stream()
                .map(order -> {
                    if (order.getPayment() == null) {
                        return createOrderLineResponseBeforePaid(order, order.getProductName());
                    }
                    return createOrderLineResponse(order, order.getProductName());
                })
                .toList();

        return new OrderListResponse(orderResponses);
    }

    private OrderLineResponse createOrderLineResponse(Order order, String productName) {
        return OrderLineResponse.builder()
                .productName(productName)
                .price(order.getPaymentPrice())
                .orderUid(order.getOrderUid())
                .orderStatus(order.getOrderStatus().toString())
                .build();
    }

    private OrderLineResponse createOrderLineResponseBeforePaid(Order order, String productName) {
        return OrderLineResponse.builder()
                .productName(productName)
                .price(0L)
                .orderUid(order.getOrderUid())
                .orderStatus(OrderStatus.WAITING.toString())
                .build();
    }

    @Override
    @Transactional
    public void changeOrderStatusToPaid(String orderUid) {
        Order order = orderRepository.findByOrderUid(orderUid)
                .orElseThrow(() -> new RestApiException(OrderErrorCode.CAN_NOT_FOUND_ORDER));
        order.paidOrder();
        log.info("OrderService order = {}", order.getId());

        Product product = productRepository.findById(order.getProductId())
                .orElseThrow(() -> new RestApiException(ProductErrorCode.PRODUCT_NOT_FOUND));
        product.increaseOrder();
    }
}
