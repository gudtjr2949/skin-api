package com.personal.skin_api.review.service;

import com.personal.skin_api.review.service.dto.request.*;
import com.personal.skin_api.review.service.dto.response.ReviewListResponse;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    void createReview(ReviewCreateServiceRequest request);
    ReviewListResponse findReviewList(ReviewFindListServiceRequest request);
    ReviewListResponse findMyReviewList(ReviewFindMyListServiceRequest request);
    int findProductReviewCount(Long productId);
    void modifyReview(ReviewModifyServiceRequest request);
    void deleteReview(ReviewDeleteServiceRequest request);
}