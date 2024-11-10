package com.example.controller.customer;

import com.example.contants.Message;
import com.example.dtos.BaseResponse;
import com.example.dtos.response.CustomerDTO;
import com.example.service.CustomerService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@Validated
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping(
            path = "/{customerId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BaseResponse<CustomerDTO> getCustomerDetails(
            @PathVariable
            @NotNull(message = Message.ID_IS_REQUIRED)
            String customerId
    ) {
        return new BaseResponse<>(
                true,
                HttpStatus.OK,
                Message.CUSTOMER_RETRIEVED_SUCCESSFULLY,
                customerService.getCustomerByCustomerId(customerId)
        );
    }
}
