package com.producerconsumer.billing.services.serviceImpl;

import com.producerconsumer.billing.models.Billing;
import com.producerconsumer.billing.repository.IBillingRepository;
import com.producerconsumer.billing.services.IBillingService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BillingService implements IBillingService {

    private IBillingRepository billingRepository;
    private final Logger logger = LoggerFactory.getLogger(BillingService.class);

    @Override
    public Long saveBillingEvent(Billing billing) {
        this.billingRepository.save(billing);
        logger.info("saved billing {}", billing.getCompanyId());
        return billing.getBillingId();
    }
}
