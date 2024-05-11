package com.producer.model.dto;

import com.producer.constants.ActionType;

public record UpgradeRequestDto(
        Long companyId,
        ActionType actionType,
        String currentSku,
        String previousSku
) {
}
