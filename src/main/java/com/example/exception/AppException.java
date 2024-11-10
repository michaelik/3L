package com.example.exception;

import com.example.dtos.BaseResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AppException {

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse<?> handleInvalidRequest(
            HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException
    ) {
        return new BaseResponse<>(
                false,
                HttpStatus.METHOD_NOT_ALLOWED,
                httpRequestMethodNotSupportedException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<?> handleInvalidArgument(
            MethodArgumentNotValidException methodArgumentNotValidException
    ) {
        List<String> errors = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new BaseResponse<>(
                false,
                HttpStatus.BAD_REQUEST,
                errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse<?> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException) {
        var errors = constraintViolationException
                .getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return new BaseResponse<>(
                false,
                HttpStatus.BAD_REQUEST,
                errors);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> handleException(
            Exception exception) {

        return new BaseResponse<>(
                false,
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage());
    }
}
