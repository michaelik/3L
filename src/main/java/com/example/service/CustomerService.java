package com.example.service;

import com.example.dtos.response.CustomerDTO;

public interface CustomerService {
    CustomerDTO getCustomerByCustomerId(String customerId);
}
