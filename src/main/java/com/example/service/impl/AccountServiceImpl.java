package com.example.service.impl;

import com.example.contants.Message;
import com.example.dtos.request.AccountRequestDTO;
import com.example.entity.Account;
import com.example.entity.Customer;
import com.example.enums.AccountType;
import com.example.exception.CustomerNotFoundException;
import com.example.exception.DuplicateAccountException;
import com.example.respository.AccountRepository;
import com.example.respository.CustomerRepository;
import com.example.service.AccountService;
import com.example.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;


    @Override
    @Transactional
    public void createCurrentAccount(String customerId, AccountRequestDTO request) {
        Customer existingCustomer = getCustomerById(customerId);

        if (hasCurrentAccount(existingCustomer)) {
            throw new DuplicateAccountException(Message.CUSTOMER_HAVE_CURRENT_ACCOUNT);
        }

        Account currentAccount = createCurrentAccountForCustomer(existingCustomer);
        transactionService.processInitialCredit(request.initialCredit(), currentAccount);
    }

    private boolean hasCurrentAccount(Customer customer) {
        return accountRepository.existsByCustomerAndAccountType(
                customer,
                AccountType.CURRENT
        );
    }

    private Customer getCustomerById(String customerId) {
        return customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        Message.CUSTOMER_NOT_FOUND.formatted(customerId)
                ));
    }

    private Account createCurrentAccountForCustomer(Customer existingCustomer) {
        Account newCurrentAccount = Account.builder()
                .accountType(AccountType.CURRENT)
                .customer(existingCustomer)
                .build();
        return accountRepository.save(newCurrentAccount);
    }
}
