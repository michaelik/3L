package com.example.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class BaseResponse<T> extends ResponseEntity<ResponseBody<T>> {

    private HttpStatus status;
    private boolean success;
    private String message;
    private List<String> messages;
    @Nullable
    private T data;
    private LocalDateTime time;

    public BaseResponse() {
        super(HttpStatus.OK);
    }

    public BaseResponse(
            @JsonProperty("success") boolean success,
            @JsonProperty("status") @Nullable HttpStatus status,
            @JsonProperty("message") String message,
            @JsonProperty("messages") List<String> messages,
            @JsonProperty("data") T data,
            @JsonProperty("time") LocalDateTime time
    ) {
        super(new ResponseBody<>(success, message, messages, data, time), status != null ? status : HttpStatus.OK);
        this.status = status != null ? status : HttpStatus.OK;
        this.success = success;
        this.message = message;
        this.messages = messages;
        this.data = data;
        this.time = time;
    }

    public BaseResponse(boolean success, HttpStatus status, String message) {
        super(new ResponseBody<>(success, message, null, null, LocalDateTime.now()), status);
        this.status = status;
        this.success = success;
        this.message = message;
    }

    public BaseResponse(boolean success, HttpStatus status, List<String> messages) {
        super(new ResponseBody<>(success, null, messages, null, LocalDateTime.now()), status);
        this.status = status;
        this.success = success;
        this.messages = messages;
    }

    public BaseResponse(boolean success, HttpStatus status, String message, @Nullable T data) {
        super(new ResponseBody<>(success, message, null, data, LocalDateTime.now()), status);
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
