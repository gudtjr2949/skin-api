package com.personal.skin_api.payment.service;

import com.personal.skin_api.AbstractIntegrationTest;
import com.personal.skin_api.common.exception.RestApiException;

import com.personal.skin_api.member.repository.entity.Member;

import com.personal.skin_api.order.repository.entity.Order;

import com.personal.skin_api.order.repository.entity.OrderStatus;
import com.personal.skin_api.payment.repository.PaymentRepository;
import com.personal.skin_api.payment.repository.entity.Payment;
import com.personal.skin_api.payment.repository.entity.impuid.ImpUid;
import com.personal.skin_api.payment.service.dto.request.PaymentCreateServiceRequest;

import com.personal.skin_api.product.repository.entity.Product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.personal.skin_api.common.util.MerchantUidGenerator.*;
import static org.assertj.core.api.Assertions.*;


class PaymentServiceTest extends AbstractIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @AfterEach
    void tearDown() {
        paymentRepository.deleteAllInBatch();
    }

    @Test
    void 결재_정보를_생성한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = generateMerchantUid();
        Order order = createOrder(member, product, orderUid);

        String impUid = "imp_123456789876";
        LocalDateTime now = LocalDateTime.now();
        String payMethod = "card";

        PaymentCreateServiceRequest request = PaymentCreateServiceRequest.builder()
                .impUid(impUid)
                .email(member.getEmail())
                .orderUid(orderUid)
                .price(product.getPrice())
                .paidAt(now)
                .payMethod(payMethod)
                .build();

        // when
        paymentService.createPayment(request);

        Optional<Payment> byImpUid = paymentRepository.findByImpUid(new ImpUid(impUid));

        // then
        assertThat(byImpUid).isPresent();
        assertThat(byImpUid.get().getImpUid()).isEqualTo(impUid);
    }

    @Test
    void 결제에_해당하는_주문정보가_없다면_예외가_발생한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = generateMerchantUid();
        Order order = createOrder(member, product, orderUid);

        String impUid = "imp_123456789876";
        LocalDateTime now = LocalDateTime.now();
        String payMethod = "card";
        String wrongOrderUid = orderUid + "wrong";

        PaymentCreateServiceRequest request = PaymentCreateServiceRequest.builder()
                .impUid(impUid)
                .email(member.getEmail())
                .orderUid(wrongOrderUid)
                .price(product.getPrice())
                .paidAt(now)
                .payMethod(payMethod)
                .build();

        // when & then
        assertThatThrownBy(() -> paymentService.createPayment(request))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 결제_요청을_보낸_사용자와_주문_정보에_있는_사용자의_정보가_다를_경우_예외가_발생한다() {
        // given
        Member member = createGeneralMember();
        Member otherMember = createGeneralMemberWithEmail("other123@naver.com");
        Product product = createProduct(member);
        String orderUid = generateMerchantUid();
        Order order = createOrder(member, product, orderUid);

        String impUid = "imp_123456789876";
        LocalDateTime now = LocalDateTime.now();
        String payMethod = "card";

        PaymentCreateServiceRequest request = PaymentCreateServiceRequest.builder()
                .impUid(impUid)
                .email(otherMember.getEmail())
                .orderUid(orderUid)
                .price(product.getPrice())
                .paidAt(now)
                .payMethod(payMethod)
                .build();

        // when & then
        assertThatThrownBy(() -> paymentService.createPayment(request))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 결제에_성공한_경우_주문의_상태가_결제완료로_변경된다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = generateMerchantUid();
        Order order = createOrder(member, product, orderUid);

        String impUid = "imp_123456789876";
        LocalDateTime now = LocalDateTime.now();
        String payMethod = "card";

        PaymentCreateServiceRequest request = PaymentCreateServiceRequest.builder()
                .impUid(impUid)
                .email(member.getEmail())
                .orderUid(orderUid)
                .price(product.getPrice())
                .paidAt(now)
                .payMethod(payMethod)
                .build();

        paymentService.createPayment(request);

        // when
        Optional<Order> byOrderUid = orderRepository.findByOrderUid(orderUid);

        // then
        assertThat(byOrderUid).isPresent();
        assertThat(byOrderUid.get().getOrderStatus()).isEqualTo(OrderStatus.PAID);
    }

}