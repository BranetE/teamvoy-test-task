package com.temvoy.test.task.service.impl;

import com.temvoy.test.task.dto.AuthRequestDto;
import com.temvoy.test.task.dto.AuthResponseDto;
import com.temvoy.test.task.dto.CreateUserDto;
import com.temvoy.test.task.model.Role;
import com.temvoy.test.task.model.User;
import com.temvoy.test.task.repository.UserRepository;
import com.temvoy.test.task.security.JwtProvider;
import com.temvoy.test.task.service.AuthService;
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
