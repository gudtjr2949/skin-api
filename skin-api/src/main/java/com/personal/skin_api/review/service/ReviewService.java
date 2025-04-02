package com.personal.skin_api.review.service;

import com.personal.skin_api.review.service.dto.request.ReviewCreateServiceRequest;
import com.personal.skin_api.review.service.dto.request.ReviewDeleteServiceRequest;
import com.personal.skin_api.review.service.dto.request.ReviewFindListServiceRequest;
import com.personal.skin_api.review.service.dto.request.ReviewModifyServiceRequest;
import com.personal.skin_api.review.service.dto.response.ReviewListResponse;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    void createReview(ReviewCreateServiceRequest request);
    ReviewListResponse findReviewList(ReviewFindListServiceRequest request);
    int findProductReviewCount(Long productId);
    void modifyReview(ReviewModifyServiceRequest request);
    void deleteReview(ReviewDeleteServiceRequest request);
}