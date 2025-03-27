package com.personal.skin_api.order.repository;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderUid(String orderUid);
    Optional<Order> findByOrderUidAndMember(String orderUid, Member member);
}