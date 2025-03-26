package com.personal.skin_api.order.service;

import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.order.repository.OrderRepository;
import com.personal.skin_api.order.repository.PaymentRepository;
import com.personal.skin_api.order.repository.entity.Payment;
import com.personal.skin_api.order.service.dto.request.OrderSaveServiceRequest;
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
    void 주문을_생성한다() throws IamportResponseException, IOException {
        // given
        OrderSaveServiceRequest request = OrderSaveServiceRequest.builder()
                .email(member.getEmail())
                .productId(product.getId())
                .impUid(payment.getImpUid())
                .build();

        // when
        orderService.saveOrder(request);

        // then

    }

    private static Payment createPayment(LocalDateTime paidAt) {
        return Payment.builder()
                .impUid("imp_370615...")
                .price(100L)
                .payMethod("card")
                .payInfo("현대카드 942012*********1")
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
        int price = 10_000;

        return Product.builder()
                .member(member)
                .productName(productName)
                .productContent(productContent)
                .fileUrl(fileUrl)
                .price(price)
                .build();
    }

}