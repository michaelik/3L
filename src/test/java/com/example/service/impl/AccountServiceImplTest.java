package com.example.service.impl;

import com.example.dtos.request.AccountRequestDTO;
import com.example.entity.Account;
import com.example.entity.Customer;
import com.example.enums.AccountType;
import com.example.exception.CustomerNotFoundException;
import com.example.exception.DuplicateAccountException;
import com.example.respository.AccountRepository;
import com.example.respository.CustomerRepository;
import com.example.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCurrentAccount_NoExistingAccount_Successful() {
        // Given
        String customerId = "3LINE001";
        AccountRequestDTO request = AccountRequestDTO.builder()
                .initialCredit(BigDecimal.valueOf(100.0))
                .build();
        Customer existingCustomer = Customer.builder()
                .customerId(customerId)
                .build();
        Account newAccount = Account.builder()
                .accountType(AccountType.CURRENT)
                .customer(existingCustomer)
                .build();

        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(existingCustomer));
        when(accountRepository.existsByCustomerAndAccountType(existingCustomer, AccountType.CURRENT)).thenReturn(false);
        when(accountRepository.save(newAccount)).thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(transactionService).processInitialCredit(request.initialCredit(), newAccount);

        // When
        assertDoesNotThrow(() -> accountService.createCurrentAccount(customerId, request));

        // Then
        verify(customerRepository, times(1)).findByCustomerId(customerId);
        verify(accountRepository, times(1)).existsByCustomerAndAccountType(
                existingCustomer,
                AccountType.CURRENT);
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(transactionService, times(1)).processInitialCredit(
                request.initialCredit(),
                newAccount);
    }

    @Test
    void createCurrentAccount_ExistingAccount_ThrowsDuplicateAccountException() {
        // Given
        String customerId = "3LINE001";
        AccountRequestDTO request = AccountRequestDTO.builder()
                .initialCredit(BigDecimal.valueOf(100.0))
                .build();
        Customer existingCustomer = Customer.builder()
                .customerId(customerId)
                .build();

        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(existingCustomer));
        when(accountRepository.existsByCustomerAndAccountType(existingCustomer, AccountType.CURRENT)).thenReturn(true);

        // When
        assertThrows(DuplicateAccountException.class, () -> accountService.createCurrentAccount(customerId, request));

        // Then
        verify(customerRepository, times(1)).findByCustomerId(customerId);
        verify(accountRepository, times(1)).existsByCustomerAndAccountType(
                existingCustomer,
                AccountType.CURRENT);
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionService, never()).processInitialCredit(BigDecimal.valueOf(anyDouble()), any(Account.class));
    }

    @Test
    void createCurrentAccount_CustomerNotFound_ThrowsCustomerNotFoundException() {
        // Given
        String customerId = "3LINE001";
        AccountRequestDTO request = AccountRequestDTO.builder()
                .initialCredit(BigDecimal.valueOf(100.0))
                .build();

        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        // When
        assertThrows(CustomerNotFoundException.class, () -> accountService.createCurrentAccount(customerId, request));

        // Then
        verify(customerRepository, times(1)).findByCustomerId(customerId);
        verify(accountRepository, never()).existsByCustomerAndAccountType(any(Customer.class), any(AccountType.class));
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionService, never()).processInitialCredit(BigDecimal.valueOf(anyDouble()), any(Account.class));
    }
}