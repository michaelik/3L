package com.example.respository;

import com.example.entity.Account;
import com.example.entity.Customer;
import com.example.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByCustomerAndAccountType(Customer customer, AccountType accountType);
}
