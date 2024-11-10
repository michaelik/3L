package com.example.dtos.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record AccountDTO (
        UUID id,
        String accountType,
        BigDecimal balance,
        List<TransactionDTO> transaction
) {
}
