package com.personal.skin_api;

import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.order.repository.OrderRepository;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.payment.repository.PaymentRepository;
import com.personal.skin_api.payment.repository.entity.Payment;
import com.personal.skin_api.payment.service.PaymentService;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected PaymentRepository paymentRepository;

    @AfterEach
    void tearDown() {
        paymentRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    /**
     * 회원 정보
     */
    private static String email = "asd123@naver.com";
    private static String password = "asd1234!";
    private static String memberName = "홍길동";
    private static String nickname = "길동짱짱";
    private static String phone = "01012345678";

    /**
     * 제품 정보
     */
    private static String productName = "형석이의 스킨";
    private static String productContent = "아주 예쁜 스킨입니다!";
    private static String fileUrl = "s3://hyeongseok-skin/fileUrl";
    private static Long price = 10_000L;


    /**
     * 결제 정보
     */
    private static String impUid = "imp_123456789876";
    private static String payMethod = "card";

    protected Member createGeneralMember() {
        return memberRepository.save(Member.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.GENERAL)
                .build());
    }

    protected Member createGeneralMemberWithEmail(final String email) {
        return memberRepository.save(Member.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.GENERAL)
                .build());
    }

    protected Product createProduct(Member member) {
        return productRepository.save(Product.builder()
                .member(member)
                .productName(productName)
                .productContent(productContent)
                .fileUrl(fileUrl)
                .price(price)
                .build());
    }

    protected Order createOrder(final Member member, final Product product, final String orderUid) {
        return orderRepository.save(Order.createBeforePayOrder(member, product, orderUid));
    }

    protected Payment createPayment(final Order order) {
        return paymentRepository.save(Payment.builder()
                .price(price)
                .paidAt(LocalDateTime.now())
                .payMethod(payMethod)
                .impUid(impUid)
                .order(order)
                .build());
    }
}
