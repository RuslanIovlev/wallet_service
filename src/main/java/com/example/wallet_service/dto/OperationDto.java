package com.example.wallet_service.dto;

import com.example.wallet_service.enums.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OperationDto(
        @NotNull
        UUID wallet_id,
        @NotNull
        OperationType operationType,
        @Positive
        long amount) {
}
