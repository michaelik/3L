package com.example.mappers;

import com.example.dtos.response.AccountDTO;
import com.example.entity.Account;

import java.util.List;

public interface AccountMapper {
    static AccountDTO toDTO(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .transaction(account.getTransactions() != null
                        ? account.getTransactions().stream().map(TransactionMapper::toDTO).toList()
                        : List.of())
                .build();
    }
}
