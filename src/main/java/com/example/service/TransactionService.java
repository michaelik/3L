package com.example.service;

import com.example.entity.Account;

import java.math.BigDecimal;

public interface TransactionService {
    void processInitialCredit(BigDecimal initialCredit, Account currentAccount);
}
