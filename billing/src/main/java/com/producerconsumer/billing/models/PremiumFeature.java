package com.producerconsumer.billing.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//@JsonDeserialize
@Getter
public class PremiumFeature {
    @Setter
    Long companyId;
    final String featureName = "ADVANCE STORAGE";
    final boolean subscribed = true;
    final String subscriptionDate = LocalDateTime.now().toString();

}
