package com.producer.model.dto;

public record PremiumFeature(
        Long companyId,
        String featureName,
        String subscriptionDate,
        Boolean subscribed
) {
}
