package com.personal.skin_api.order.service;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.order.OrderErrorCode;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.order.repository.OrderRepository;
import com.personal.skin_api.order.repository.PaymentRepository;
import com.personal.skin_api.order.repository.QOrderRepository;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.order.repository.entity.Payment;
import com.personal.skin_api.order.service.dto.request.OrderCreateBeforePaidServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderCreateTableServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderDetailServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderListServiceRequest;
import com.personal.skin_api.order.service.dto.response.OrderCreateTableResponse;
import com.personal.skin_api.order.service.dto.response.OrderDetailResponse;
import com.personal.skin_api.order.service.dto.response.OrderListResponse;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.personal.skin_api.common.util.MerchantUidGenerator.makeFormattedDay;
import static com.personal.skin_api.order.repository.QOrderRepository.*;
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
        String impUid = "imp_370615..";
        Payment payment = createPayment(impUid, LocalDateTime.now(), findOrder.get());
        paymentRepository.save(payment);

        OrderDetailServiceRequest orderDetailServiceRequest = OrderDetailServiceRequest.builder()
                .orderUid(orderUid)
                .email(member.getEmail())
                .build();

        // when
        OrderDetailResponse detailOrder = orderService.findOrder(orderDetailServiceRequest);

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
        String impUid = "imp_370615..";
        Payment payment = createPayment(impUid, LocalDateTime.now(), findOrder.get());
        paymentRepository.save(payment);

        OrderDetailServiceRequest orderDetailServiceRequest = OrderDetailServiceRequest.builder()
                .orderUid(orderUid)
                .email(member.getEmail())
                .build();

        // when
        OrderDetailResponse detailOrder = orderService.findOrder(orderDetailServiceRequest);

        // then
        assertThat(detailOrder.getPayMethod()).isEqualTo(payment.getPayMethod());
        assertThat(detailOrder.getPrice()).isEqualTo(payment.getPrice());
    }

    @Test
    void 나의_주문_리스트를_조회한다() {
        // given
        List<String> orderUidList = IntStream.range(0, ORDER_PAGE_SIZE)
                .mapToObj(i -> OrderCreateBeforePaidServiceRequest.builder()
                        .email(member.getEmail())
                        .productId(product.getId())
                        .build())
                .map(orderService::createBeforePaidOrder)
                .collect(Collectors.toList());

        List<String> impUidList = List.of("imp_123456..", "imp_654321..", "imp_789012..", "imp_345678..", "imp_901234..");

        // 주문을 조회하고 결제 정보를 생성한 후 저장
        List<Payment> payments = IntStream.range(0, 5)
                .mapToObj(i -> {
                    Order order = orderRepository.findByOrderUid(orderUidList.get(i))
                            .orElseThrow(() -> new RestApiException(OrderErrorCode.CAN_NOT_FOUND_ORDER));
                    return createPayment(impUidList.get(i), LocalDateTime.now(), order);
                })
                .collect(Collectors.toList());

        paymentRepository.saveAll(payments);

        OrderListServiceRequest request = OrderListServiceRequest.builder()
                .orderId(0L)
                .keyword("")
                .year(LocalDateTime.now().getYear())
                .email(member.getEmail())
                .build();

        // when
        OrderListResponse myOrderList = orderService.findMyOrderList(request);

        // then
        assertThat(myOrderList).isNotNull();
        assertThat(myOrderList.getOrderResponses()).hasSize(ORDER_PAGE_SIZE);
        assertThat(myOrderList.getOrderResponses().get(0).getOrderUid()).isEqualTo(orderUidList.get(ORDER_PAGE_SIZE-1));
        assertThat(myOrderList.getOrderResponses().get(ORDER_PAGE_SIZE-1).getOrderUid()).isEqualTo(orderUidList.get(0));
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

    private static Payment createPayment(String impUid, LocalDateTime paidAt, Order order) {
        return Payment.builder()
                .impUid(impUid)
                .order(order)
                .price(100L)
                .payMethod("card")
                .paidAt(paidAt)
                .build();
    }
}