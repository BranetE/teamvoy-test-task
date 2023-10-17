package com.example.testtasktemvoy.service;

import com.example.testtasktemvoy.dto.AuthRequestDto;
import com.example.testtasktemvoy.dto.AuthResponseDto;
import com.example.testtasktemvoy.dto.CreateUserDto;
import com.example.testtasktemvoy.model.User;

public interface AuthService {
    void register(CreateUserDto createUserDto);

    AuthResponseDto login(AuthRequestDto authRequestDto);
}
