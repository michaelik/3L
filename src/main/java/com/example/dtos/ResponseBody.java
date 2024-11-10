package com.example.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseBody<T>(
        boolean success,

        String message,

        List<String> messages,

        @Nullable
        T data,
        LocalDateTime time
) {
}
