package com.example.testtasktemvoy.exception;

import com.example.testtasktemvoy.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseMessage handleEntityNotFoundException(HttpServletRequest request, RuntimeException exception){
        return ResponseMessage.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .url(request.getRequestURL().toString())
                .message(exception.getMessage())
                .stackTrace(Arrays.toString(exception.getStackTrace()))
                .build();
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleRuntimeException(HttpServletRequest request, RuntimeException exception){
        return ResponseMessage.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .url(request.getRequestURL().toString())
                .message(exception.getMessage())
                .stackTrace(Arrays.toString(exception.getStackTrace()))
                .build();
    }
}
