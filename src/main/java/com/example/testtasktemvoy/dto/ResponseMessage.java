package com.example.testtasktemvoy.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessage {
    Integer status;
    String exception;
    String url;
    String message;
    String stackTrace;
}
