package com.personal.skin_api.common.batch;

import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.product.repository.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductItemReader implements ItemReader<Product> {

    private long count = 0;
    private static final long LIMIT = 1_000_000;
    private final MemberRepository memberRepository;

    @Override
    public Product read() {
        if (count >= LIMIT) {
            return null; // 데이터 끝
        }

        Member member = memberRepository.findById(1L).get();
        count++;

        return Product.builder()
                .member(member)
                .productName("ProductName " + count)
                .productContent("Content " + count)
                .blogUrl("https://gudtjr2949.tistory.com")
                .price(1_000L)
                .fileUrl("https://skin-uploads.s3.ap-northeast-2.amazonaws.com/skin/069d4bc0-4bb5-4d3d-9315-44c9b54fbed4_test.zip")
                .thumbnailUrl("https://skin-uploads.s3.ap-northeast-2.amazonaws.com/skin/f9184c82-bbc8-4f6d-8009-0c570d5dfe4a_%E1%84%86%E1%85%B5%E1%84%82%E1%85%A6%E1%86%B7.png")
                .build();
    }
}
