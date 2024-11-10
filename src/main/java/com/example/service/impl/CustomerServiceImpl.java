package com.example.service.impl;

import com.example.contants.Message;
import com.example.dtos.response.CustomerDTO;
import com.example.exception.CustomerNotFoundException;
import com.example.mappers.CustomerMapper;
import com.example.respository.CustomerRepository;
import com.example.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;


    @Override
    @Transactional
    public CustomerDTO getCustomerByCustomerId(String customerId) {
        return customerRepository.findByCustomerId(customerId)
                .map(CustomerMapper::toDTO)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                Message.CUSTOMER_NOT_FOUND.formatted(customerId)
                ));
    }
}
