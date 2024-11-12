package com.example.service.impl;

import com.example.contants.Message;
import com.example.entity.Account;
import com.example.entity.Transaction;
import com.example.exception.AccountNotFoundException;
import com.example.respository.AccountRepository;
import com.example.respository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Account account;
    private UUID accountId;
    private BigDecimal initialCredit;

    @BeforeEach
    void setUp() {
        accountId = UUID.randomUUID();
        initialCredit = new BigDecimal("100.00");
        account = Account.builder()
                .id(accountId)
                .balance(new BigDecimal("0.00"))
                .build();
    }

    @Test
    void processInitialCredit_AccountExists_Success() {
        // Given
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(accountRepository.save(account)).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        assertDoesNotThrow(() -> transactionService.processInitialCredit(initialCredit, account));

        // Then
        verify(accountRepository, times(1)).findById(accountId);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(1)).save(account);

        BigDecimal expectedBalance = new BigDecimal("100.00");
        assert(account.getBalance().compareTo(expectedBalance) == 0);
    }

    @Test
    void processInitialCredit_AccountNotFound_ThrowsException() {
        // Given
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // When
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () ->
                transactionService.processInitialCredit(initialCredit, account)
        );

        // Then
        assertEquals(Message.ACCOUNT_NOT_FOUND.formatted(accountId), exception.getMessage());
        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(accountRepository, never()).save(any(Account.class));
    }
}