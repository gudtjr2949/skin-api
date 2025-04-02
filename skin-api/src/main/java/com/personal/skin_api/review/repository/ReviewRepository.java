package com.personal.skin_api.review.repository;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.review.repository.entity.Review;
import com.personal.skin_api.review.repository.entity.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
    List<Review> findByMember(Member member);
    Optional<Review> findByIdAndReviewStatus(Long id, ReviewStatus reviewStatus);
    Optional<Review> findByMemberAndProductAndOrder(Member member, Product product, Order order);
}
