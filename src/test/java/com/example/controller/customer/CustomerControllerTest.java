package com.example.controller.customer;

import com.example.contants.Message;
import com.example.dtos.BaseResponse;
import com.example.dtos.response.AccountDTO;
import com.example.dtos.response.CustomerDTO;
import com.example.dtos.response.TransactionDTO;
import com.example.enums.AccountType;
import com.example.service.CustomerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {CustomerController.class})
@ExtendWith(SpringExtension.class)
class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    @Test
    void getCustomerDetails() throws Exception {
        // Given: Prepare the test data and mock behavior
        String customerId = "3LINE001";
        UUID accountId = UUID.fromString("bd1963b1-44c4-4fa9-9b2d-e76f1676beeb");
        UUID transactionId = UUID.fromString("06ee2f5f-e43b-45de-9c43-b338dbfaf0ca");

        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerId(customerId)
                .surname("Lawanson")
                .firstName("Ayo")
                .account(List.of(
                        AccountDTO.builder()
                                .id(accountId)
                                .accountType(AccountType.CURRENT)
                                .balance(new BigDecimal("100.00"))
                                .transaction(List.of(
                                        TransactionDTO.builder()
                                                .id(transactionId)
                                                .amount(new BigDecimal("100.00"))
                                                .accountType(AccountType.CREDIT)
                                                .createdAt(LocalDateTime.of(2024, 11, 10, 22, 9, 47, 761407))
                                                .build()
                                )).build()
                )).build();

        given(customerService.getCustomerByCustomerId(customerId)).willReturn(customerDTO);

        // When: Simulate the HTTP POST request to create the current account
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/customers/{customerId}", customerId)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = MockMvcBuilders.standaloneSetup(customerController).build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print(System.out))
                .andReturn();

        // Then: Deserialize the response and validate the result
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        BaseResponse<CustomerDTO> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        // Then: Validate the response properties
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.isSuccess());
        assertEquals(Message.CUSTOMER_RETRIEVED_SUCCESSFULLY, response.getMessage());
    }
}