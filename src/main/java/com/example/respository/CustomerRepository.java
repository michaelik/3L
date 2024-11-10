package com.example.respository;

import com.example.entity.Customer;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Lock(LockModeType.OPTIMISTIC)
    Optional<Customer> findByCustomerId(String customerId);


}
