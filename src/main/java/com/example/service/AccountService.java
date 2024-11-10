package com.example.service;

import com.example.dtos.request.AccountRequestDTO;

public interface AccountService {
    void createCurrentAccount(String customerId, AccountRequestDTO request);
}
