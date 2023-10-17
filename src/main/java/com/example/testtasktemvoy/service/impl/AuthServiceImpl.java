package com.example.testtasktemvoy.service.impl;

import com.example.testtasktemvoy.dto.AuthRequestDto;
import com.example.testtasktemvoy.dto.AuthResponseDto;
import com.example.testtasktemvoy.dto.CreateUserDto;
import com.example.testtasktemvoy.model.Role;
import com.example.testtasktemvoy.model.User;
import com.example.testtasktemvoy.repository.UserRepository;
import com.example.testtasktemvoy.security.JwtProvider;
import com.example.testtasktemvoy.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;

    @Override
    public void register(CreateUserDto createUserDto) {
        User user = new User();
        user.setEmail(createUserDto.getEmail());
        user.setRole(Role.USER);
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));

        if(userRepository.existsByEmail(user.getEmail())){
            throw new EntityExistsException();
        }
        userRepository.save(user);
    }

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDto.getEmail());
        String token = jwtProvider.generateToken(userDetails);
        return AuthResponseDto.builder()
                .token(token)
                .tokenType("BEARER ")
                .build();
    }
}
