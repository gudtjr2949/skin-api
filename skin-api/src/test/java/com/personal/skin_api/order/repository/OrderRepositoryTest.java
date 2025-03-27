package com.personal.skin_api.order.repository;

import com.personal.skin_api.common.util.MerchantUidGenerator;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.order.repository.entity.OrderStatus;
import com.personal.skin_api.order.repository.entity.Payment;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private Member member;

    private Product product;

    private Payment payment;

    @BeforeAll
    void setUp() {
        this.member = createMember();
        product = createProduct();
        productRepository.save(product);
        this.payment = createPayment(LocalDateTime.now());
        paymentRepository.save(payment);
    }

    @Test
    void 주문을_생성하고_조회한다() {
        // given
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = Order.createBeforePayOrder(member, product, orderUid);

        // when
        orderRepository.save(order);
        Optional<Order> findOrder = orderRepository.findById(order.getId());

        // then
        assertThat(findOrder).isPresent();
        assertThat(findOrder.get().getId()).isEqualTo(order.getId());
        assertThat(findOrder.get().getOrderStatus()).isEqualTo(OrderStatus.WAITING);
    }

    @Test
    void 주문을_생성하고_주문_관련_정보를_조회한다() {
        // given
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = Order.createBeforePayOrder(member, product, orderUid);
        orderRepository.save(order);

        // when
        Optional<Order> findOrder = orderRepository.findById(order.getId());

        // then
        assertThat(findOrder).isPresent();
        assertThat(findOrder.get().getProductName()).isEqualTo(product.getProductName());
        assertThat(findOrder.get().getMember()).isEqualTo(member.getEmail());
    }

    @Test
    void 주문을_취소한다() {
        // given
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = Order.createBeforePayOrder(member, product, orderUid);
        orderRepository.save(order);

        // when
        Optional<Order> findOrder = orderRepository.findById(order.getId());
        findOrder.get().cancelOrder();

        // then
        assertThat(findOrder).isPresent();
        assertThat(findOrder.get().getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    private static Payment createPayment(LocalDateTime paidAt) {
        return Payment.builder()
                .impUid("imp_370615...")
                .price(100L)
                .payMethod("card")
                .paidAt(paidAt)
                .build();
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
}