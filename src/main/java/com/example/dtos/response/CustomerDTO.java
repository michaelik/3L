package com.example.dtos.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CustomerDTO (
        String customerId,
        String surname,
        String firstName,
        List<AccountDTO> account
) {
}
