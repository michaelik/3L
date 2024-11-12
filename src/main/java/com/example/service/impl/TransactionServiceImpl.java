package com.example.service.impl;

import com.example.contants.Message;
import com.example.entity.Account;
import com.example.entity.Transaction;
import com.example.enums.AccountType;
import com.example.exception.AccountNotFoundException;
import com.example.respository.AccountRepository;
import com.example.respository.TransactionRepository;
import com.example.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;


    @Override
    @Transactional
    public void processInitialCredit(BigDecimal initialCredit, Account account) {
        Account currentAccount = getAccountById(account.getId());
        Transaction newTransaction = createTransaction(currentAccount, initialCredit);
        transactionRepository.save(newTransaction);

        BigDecimal newBalance = currentAccount.getBalance().add(initialCredit);
        currentAccount.setBalance(newBalance);
        accountRepository.save(currentAccount);
    }

    private Account getAccountById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(
                        Message.ACCOUNT_NOT_FOUND.formatted(id)
                ));
    }

    private Transaction createTransaction(Account currentAccount, BigDecimal initialCredit) {
        return Transaction.builder()
                .amount(initialCredit)
                .accountType(AccountType.CREDIT)
                .account(currentAccount)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
