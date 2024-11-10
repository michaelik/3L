package com.example.dtos.response;

import com.example.enums.AccountType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TransactionDTO(
        UUID id,
        BigDecimal amount,
        AccountType accountType,
        LocalDateTime createdAt
) {
}
