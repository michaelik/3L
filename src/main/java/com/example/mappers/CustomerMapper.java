package com.example.mappers;

import com.example.dtos.response.CustomerDTO;
import com.example.entity.Customer;

import java.util.List;

public interface CustomerMapper {
    static CustomerDTO toDTO(Customer customer){
        return CustomerDTO.builder()
                .customerId(customer.getCustomerId())
                .surname(customer.getSurname())
                .firstName(customer.getFirstName())
                .account(customer.getAccounts() != null
                        ? customer.getAccounts().stream().map(AccountMapper::toDTO).toList()
                        : List.of())
                .build();
    }
}
