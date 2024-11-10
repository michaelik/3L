package com.example.controller.account;


import com.example.contants.Message;
import com.example.dtos.BaseResponse;
import com.example.dtos.request.AccountRequestDTO;
import com.example.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@Validated
@Slf4j
public class AccountController {
    private final AccountService accountService;


    @PostMapping(
            path = "/{customerId}",
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public BaseResponse<?> createCurrentAccount(
            @PathVariable
            @NotNull(message = Message.ID_IS_REQUIRED)
            String customerId,
            @RequestBody @Valid
            AccountRequestDTO request
    ) {
        accountService.createCurrentAccount(customerId, request);
        return new BaseResponse<>(
                true,
                HttpStatus.OK,
                Message.CURRENT_ACCOUNT_CREATED_SUCCESSFULLY
        );
    }
}
