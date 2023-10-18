package com.temvoy.test.task.service;

import com.temvoy.test.task.dto.AuthRequestDto;
import com.temvoy.test.task.dto.AuthResponseDto;
import com.temvoy.test.task.dto.CreateUserDto;

public interface AuthService {
    void register(CreateUserDto createUserDto);

    AuthResponseDto login(AuthRequestDto authRequestDto);
}
