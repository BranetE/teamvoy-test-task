package com.temvoy.test.task.controller;

import com.temvoy.test.task.dto.AuthRequestDto;
import com.temvoy.test.task.dto.AuthResponseDto;
import com.temvoy.test.task.dto.CreateUserDto;
import com.temvoy.test.task.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(authRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@Valid @RequestBody CreateUserDto createUserDto){
        authService.register(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
