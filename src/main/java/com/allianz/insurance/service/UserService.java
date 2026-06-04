package com.allianz.insurance.service;

import com.allianz.insurance.dto.auth.AuthResponse;
import com.allianz.insurance.dto.auth.LoginRequest;
import com.allianz.insurance.dto.auth.RegisterRequest;
import com.allianz.insurance.entity.User;
import com.allianz.insurance.exception.InvalidCredentialsException;
import com.allianz.insurance.exception.UserAlreadyExistsException;
import com.allianz.insurance.repository.UserRepository;
import com.allianz.insurance.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest request) {

        log.info("Registration request received for email: {}",
                request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            log.warn("Registration failed. User already exists with email: {}",
                    request.getEmail());

            throw new UserAlreadyExistsException(
                    "User already exists with email: " + request.getEmail());
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        log.info("User registered successfully with email: {}",
                request.getEmail());
    }

    public AuthResponse login(LoginRequest request) {

        log.info("Login request received for email: {}",
                request.getEmail());

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> {

                    log.warn("Login failed. User not found with email: {}",
                            request.getEmail());

                    return new InvalidCredentialsException(
                            "Invalid email or password");
                });

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            log.warn("Login failed. Invalid password for email: {}",
                    request.getEmail());

            throw new InvalidCredentialsException(
                    "Invalid email or password");
        }

        log.info("User logged in successfully with email: {}",
                request.getEmail());

        String token = jwtUtil.generateToken(
                user.getEmail());

        log.info("JWT token generated successfully for {}",
                user.getEmail());

        return new AuthResponse(
                token,
                "Login Successful");
    }
}