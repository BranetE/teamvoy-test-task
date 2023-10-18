package com.temvoy.test.task.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestDto {
    String email;
    String password;
}
