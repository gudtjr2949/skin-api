package com.personal.skin_api.order.repository;

import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.order.repository.entity.Payment;

import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.personal.skin_api.order.repository.QOrderRepository.ORDER_PAGE_SIZE;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QOrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private QOrderRepository qOrderRepository;

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    private Member member;
    private Product product;
    private List<Payment> payments;
    private List<Order> orders;

    @BeforeEach
    void beforeEach() {
        queryFactory = new JPAQueryFactory(em);
    }

    @BeforeAll
    void setUp() {
        this.member = createMember();
        this.product = createProduct();
        this.payments = Stream.generate(() -> createPayment(LocalDateTime.now()))
                .limit(ORDER_PAGE_SIZE)
                .toList();

        this.orders = IntStream.range(0, ORDER_PAGE_SIZE)
                .mapToObj(i -> Order.createBeforePayOrder(member, product))
                .toList();

        orderRepository.saveAll(orders);
    }

    @Test
    void 회원_정보가_일치하는_주문목록_첫_장을_조회한다() {
        // given
        Order firstOrder = orders.get(orders.size()-1);
        Order lastOrder = orders.get(0);

        // when
        List<Order> myOrders = qOrderRepository.findMyOrders(0L, member);

        // then
        assertThat(myOrders).hasSize(ORDER_PAGE_SIZE);
        assertThat(myOrders.get(0).getId()).isEqualTo(firstOrder.getId());
        assertThat(myOrders.get(ORDER_PAGE_SIZE-1).getId()).isEqualTo(lastOrder.getId());
    }

    private Payment createPayment(LocalDateTime paidAt) {
        return paymentRepository.save(Payment.builder()
                .impUid("imp_370615...")
                .price(100L)
                .payMethod("card")
                .paidAt(paidAt)
                .build());
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
        int price = 10_000;

        return productRepository.save(Product.builder()
                .member(member)
                .productName(productName)
                .productContent(productContent)
                .fileUrl(fileUrl)
                .price(price)
                .build());
    }
}