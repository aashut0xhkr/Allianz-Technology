package com.allianz.insurance.service;

import com.allianz.insurance.dto.auth.LoginRequest;
import com.allianz.insurance.dto.auth.RegisterRequest;
import com.allianz.insurance.entity.User;
import com.allianz.insurance.enums.Role;
import com.allianz.insurance.exception.InvalidCredentialsException;
import com.allianz.insurance.exception.UserAlreadyExistsException;
import com.allianz.insurance.repository.UserRepository;
import com.allianz.insurance.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUserSuccessfully() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("admin");
        request.setEmail("admin@gmail.com");
        request.setPassword("admin123");
        request.setRole(Role.ADMIN);

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(anyString()))
                .thenReturn("encodedPassword");

        userService.register(request);

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("admin@gmail.com");

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(new User()));

        assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.register(request));
    }

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = new LoginRequest();
        request.setEmail("admin@gmail.com");
        request.setPassword("admin123");

        User user = User.builder()
                .email("admin@gmail.com")
                .password("encodedPassword")
                .role(Role.ADMIN)
                .build();

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                anyString(),
                anyString()))
                .thenReturn(true);

        when(jwtUtil.generateToken(anyString()))
                .thenReturn("jwt-token");

        assertNotNull(userService.login(request));
    }

    @Test
    void shouldThrowExceptionForInvalidPassword() {

        LoginRequest request = new LoginRequest();
        request.setEmail("admin@gmail.com");
        request.setPassword("wrong");

        User user = User.builder()
                .email("admin@gmail.com")
                .password("encodedPassword")
                .build();

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                anyString(),
                anyString()))
                .thenReturn(false);

        assertThrows(
                InvalidCredentialsException.class,
                () -> userService.login(request));
    }
}