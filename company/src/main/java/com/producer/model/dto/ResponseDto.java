package com.producer.model.dto;

public record ResponseDto(
        Long companyId,
        String companyName,
        String currentSku,
        String previousSku,
        PremiumFeature premiumFeature
) {
}
