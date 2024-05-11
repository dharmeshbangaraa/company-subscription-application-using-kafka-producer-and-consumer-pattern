package com.producerconsumer.billing.repository;

import com.producerconsumer.billing.models.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillingRepository extends JpaRepository<Billing, Long> {
}
