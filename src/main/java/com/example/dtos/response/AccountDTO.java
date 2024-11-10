package com.example.dtos.response;

import com.example.enums.AccountType;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record AccountDTO (
        UUID id,
        AccountType accountType,
        BigDecimal balance,
        List<TransactionDTO> transaction
) {
}
