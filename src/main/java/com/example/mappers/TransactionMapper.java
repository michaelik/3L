package com.example.mappers;

import com.example.dtos.response.TransactionDTO;
import com.example.entity.Transaction;

public interface TransactionMapper {
    static TransactionDTO toDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .accountType(transaction.getAccountType())
                .createdAt(transaction.getTimestamp())
                .build();
    }
}
