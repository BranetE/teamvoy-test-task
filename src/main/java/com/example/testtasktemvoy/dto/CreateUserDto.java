package com.example.testtasktemvoy.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
