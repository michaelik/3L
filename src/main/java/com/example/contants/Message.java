package com.example.contants;

public interface Message {
    String ID_IS_REQUIRED = "customer id is required";
    String CUSTOMER_NOT_FOUND = "Customer account with id [%s] not found";
    String CUSTOMER_HAVE_CURRENT_ACCOUNT = "Customer already has a current account";
    String ACCOUNT_NOT_FOUND = "Account with id [%s] not found";
    String CURRENT_ACCOUNT_CREATED_SUCCESSFULLY = "Current Account Created Successfully";
    String CUSTOMER_RETRIEVED_SUCCESSFULLY = "Customer details retrieved successfully";
}
