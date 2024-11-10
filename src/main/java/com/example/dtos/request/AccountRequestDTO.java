package com.example.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountRequestDTO(
        @NotNull
        @DecimalMin(value = "0.0", message = "Initial credit cannot be negative")
        BigDecimal initialCredit
) {
}
