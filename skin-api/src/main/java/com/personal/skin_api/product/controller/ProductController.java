package com.personal.skin_api.product.controller;

import com.personal.skin_api.common.dto.CommonResponse;
import com.personal.skin_api.product.controller.dto.request.ProductModifyRequest;
import com.personal.skin_api.product.controller.dto.request.ProductRegisterRequest;
import com.personal.skin_api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> registerProduct(@ModelAttribute ProductRegisterRequest request,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        productService.registerProduct(request.toService(userDetails.getUsername()));

        return ResponseEntity.ok().body(new CommonResponse(HttpStatus.OK.value(), "제품 등록 완료"));
    }

    @PostMapping("/modify")
    public ResponseEntity<CommonResponse> modifyProduct(@ModelAttribute ProductModifyRequest request,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        productService.modifyProduct(request.toService(userDetails.getUsername()));

        return ResponseEntity.ok().body(new CommonResponse(HttpStatus.OK.value(), "제품 수정 완료"));
    }
}
