package com.personal.skin_api.payment.repository;

import com.personal.skin_api.payment.repository.entity.Payment;
import com.personal.skin_api.payment.repository.entity.impuid.ImpUid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByImpUid(ImpUid impUid);
}
