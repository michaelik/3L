package com.example.controller.account;

import com.example.contants.Message;
import com.example.dtos.BaseResponse;
import com.example.dtos.request.AccountRequestDTO;
import com.example.service.AccountService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {AccountController.class})
@ExtendWith(SpringExtension.class)
class AccountControllerTest {

    @Autowired
    private AccountController accountController;

    @MockBean
    private AccountService accountService;

    @Test
    void createCurrentAccount() throws Exception {
        // Given: Prepare the test data and mock behavior
        String customerId = "3LINE001";
        AccountRequestDTO request = new AccountRequestDTO(new BigDecimal("100.00"));

        doNothing().when(accountService).createCurrentAccount(customerId, request);

        // When: Simulate the HTTP POST request to create the current account
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/accounts/{customerId}", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request));

        MvcResult result = MockMvcBuilders.standaloneSetup(accountController).build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print(System.out))
                .andReturn();

        // Then: Deserialize the response and validate the result
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        BaseResponse<?> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BaseResponse.class
        );

        // Then: Validate the response properties
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.isSuccess());
        assertEquals(Message.CURRENT_ACCOUNT_CREATED_SUCCESSFULLY, response.getMessage());
    }
}