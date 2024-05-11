package com.producerconsumer.billing.services;

import com.producerconsumer.billing.models.Billing;

public interface IBillingService {

    Long saveBillingEvent(Billing billing);
}
