package com.personal.skin_api.product.service;

import com.personal.skin_api.common.exception.CommonErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.MemberErrorCode;
import com.personal.skin_api.common.file.service.S3Service;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.product.service.dto.request.ProductRegisterServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final MemberRepository memberRepository;

    private final S3Service s3Service;

    @Override
    public void registerProduct(ProductRegisterServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        String fileUrl = s3Service.uploadFile(request.getFile());

        Product product = request.toEntity(member, fileUrl);

        try {
            productRepository.save(product);
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
