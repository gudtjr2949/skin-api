package com.personal.skin_api.order.service;

import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.order.repository.OrderRepository;
import com.personal.skin_api.order.repository.PaymentRepository;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.order.repository.entity.Payment;
import com.personal.skin_api.order.service.dto.request.OrderCreateBeforePaidServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderCreateTableServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderDetailRequest;
import com.personal.skin_api.order.service.dto.response.OrderCreateTableResponse;
import com.personal.skin_api.order.service.dto.response.OrderDetailResponse;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;
import com.siot.IamportRestClient.exception.IamportResponseException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.personal.skin_api.common.util.MerchantUidGenerator.makeFormattedDay;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private Member member;
    private Product product;


    @BeforeAll
    void setUp() {
        this.member = createMember();
        product = createProduct();
        productRepository.save(product);
    }

    @Test
    void 주문테이블을_생성한다() {
        // given
        OrderCreateTableServiceRequest request = OrderCreateTableServiceRequest.builder()
                .email(member.getEmail())
                .productId(product.getId())
                .build();

        // when
        OrderCreateTableResponse orderTable = orderService.createOrderTable(request);

        // then
        assertThat(orderTable.getEmail()).isEqualTo(request.getEmail());
        assertThat(orderTable.getPrice()).isEqualTo(product.getPrice());
    }
    
    @Test
    void 결제_전_주문정보를_생성한다() {
        // given
        OrderCreateBeforePaidServiceRequest request = OrderCreateBeforePaidServiceRequest.builder()
                .email(member.getEmail())
                .productId(product.getId())
                .build();
        
        // when
        String beforePaidOrder = orderService.createBeforePaidOrder(request);
        String today = makeFormattedDay();

        // then
        assertThat(beforePaidOrder).isNotNull();
        assertThat(beforePaidOrder.startsWith(today)).isTrue();
    }
    
    @Test
    void 주문정보를_상세조회한다() {
        // given
        OrderCreateBeforePaidServiceRequest orderCreateBeforePaidServiceRequest = OrderCreateBeforePaidServiceRequest.builder()
                .email(member.getEmail())
                .productId(product.getId())
                .build();
        String orderUid = orderService.createBeforePaidOrder(orderCreateBeforePaidServiceRequest);

        Optional<Order> findOrder = orderRepository.findByOrderUid(orderUid);
        Payment payment = createPayment(LocalDateTime.now(), findOrder.get());
        paymentRepository.save(payment);

        OrderDetailRequest orderDetailRequest = OrderDetailRequest.builder()
                .orderUid(orderUid)
                .email(member.getEmail())
                .build();

        // when
        OrderDetailResponse detailOrder = orderService.findOrder(orderDetailRequest);

        // then
        assertThat(detailOrder).isNotNull();
        assertThat(detailOrder.getOrderUid()).isEqualTo(orderUid);
    }

    @Test
    void 주문조회시_결제정보도_조회된다() {
        // given
        OrderCreateBeforePaidServiceRequest orderCreateBeforePaidServiceRequest = OrderCreateBeforePaidServiceRequest.builder()
                .email(member.getEmail())
                .productId(product.getId())
                .build();
        String orderUid = orderService.createBeforePaidOrder(orderCreateBeforePaidServiceRequest);

        Optional<Order> findOrder = orderRepository.findByOrderUid(orderUid);
        Payment payment = createPayment(LocalDateTime.now(), findOrder.get());
        paymentRepository.save(payment);

        OrderDetailRequest orderDetailRequest = OrderDetailRequest.builder()
                .orderUid(orderUid)
                .email(member.getEmail())
                .build();

        // when
        OrderDetailResponse detailOrder = orderService.findOrder(orderDetailRequest);

        // then
        assertThat(detailOrder.getPayMethod()).isEqualTo(payment.getPayMethod());
        assertThat(detailOrder.getPrice()).isEqualTo(payment.getPrice());
    }


    private Member createMember() {
        String email = "asd123@naver.com";
        String password = "asd1234!";
        String memberName = "홍길동";
        String nickname = "길동짱짱";
        String phone = "01012345678";
        MemberStatus status = MemberStatus.ACTIVE;
        MemberRole role = MemberRole.GENERAL;

        return memberRepository.save(Member.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .status(status)
                .role(role)
                .build());
    }

    private Product createProduct() {
        String productName = "형석이의 스킨";
        String productContent = "아주 예쁜 스킨입니다!";
        String fileUrl = "s3://hyeongseok-skin/fileUrl";
        Long price = 10_000L;

        return Product.builder()
                .member(member)
                .productName(productName)
                .productContent(productContent)
                .fileUrl(fileUrl)
                .price(price)
                .build();
    }

    private static Payment createPayment(LocalDateTime paidAt, Order order) {
        return Payment.builder()
                .impUid("imp_370615...")
                .order(order)
                .price(100L)
                .payMethod("card")
                .paidAt(paidAt)
                .build();
    }
}