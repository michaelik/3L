package com.example.service.impl;

import com.example.dtos.response.AccountDTO;
import com.example.dtos.response.CustomerDTO;
import com.example.dtos.response.TransactionDTO;
import com.example.entity.Account;
import com.example.entity.Customer;
import com.example.entity.Transaction;
import com.example.enums.AccountType;
import com.example.exception.CustomerNotFoundException;
import com.example.respository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private String customerId;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customerId = "3LINE001";

        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .amount(BigDecimal.valueOf(100.0))
                .accountType(AccountType.CREDIT)
                .timestamp(LocalDateTime.now())
                .build();

        Account account = Account.builder()
                .id(UUID.randomUUID())
                .accountType(AccountType.CURRENT)
                .balance(BigDecimal.valueOf(1000.0))
                .transactions(List.of(transaction))
                .build();

        customer = Customer.builder()
                .customerId(customerId)
                .surname("Doe")
                .firstName("John")
                .accounts(List.of(account))
                .build();
    }

    @Test
    void getCustomerByCustomerId_ExistingCustomer_ReturnsCustomerDTO() {
        // Given
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));

        // When
        CustomerDTO customerDTO = customerService.getCustomerByCustomerId(customerId);

        // Then
        assertEquals(customer.getCustomerId(), customerDTO.customerId());
        assertEquals(customer.getSurname(), customerDTO.surname());
        assertEquals(customer.getFirstName(), customerDTO.firstName());
        assertEquals(customer.getAccounts().size(), customerDTO.account().size());

        Account account = customer.getAccounts().get(0);
        AccountDTO accountDTO = customerDTO.account().get(0);
        assertEquals(account.getId(), accountDTO.id());
        assertEquals(account.getAccountType(), accountDTO.accountType());
        assertEquals(account.getBalance(), accountDTO.balance());

        Transaction transaction = account.getTransactions().get(0);
        TransactionDTO transactionDTO = accountDTO.transaction().get(0);
        assertEquals(transaction.getId(), transactionDTO.id());
        assertEquals(transaction.getAmount(), transactionDTO.amount());
        assertEquals(transaction.getAccountType(), transactionDTO.accountType());
        assertEquals(transaction.getTimestamp(), transactionDTO.createdAt());

        verify(customerRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void getCustomerByCustomerId_CustomerNotFound_ThrowsCustomerNotFoundException() {
        // Given
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        // When
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerByCustomerId(customerId));

        // Then
        verify(customerRepository, times(1)).findByCustomerId(customerId);
    }
}