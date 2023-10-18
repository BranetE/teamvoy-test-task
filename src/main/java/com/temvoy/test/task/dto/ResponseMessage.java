package com.temvoy.test.task.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessage {
    private Integer status;
    private String exception;
    private String url;
    private String message;
    private String stackTrace;
}
