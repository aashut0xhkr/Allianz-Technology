package com.allianz.insurance.controller;

import com.allianz.insurance.dto.auth.AuthResponse;
import com.allianz.insurance.dto.auth.LoginRequest;
import com.allianz.insurance.dto.auth.RegisterRequest;
import com.allianz.insurance.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        log.info("Received register API request for email: {}",
                request.getEmail());

        userService.register(request);

        log.info("Register API completed successfully for email: {}",
                request.getEmail());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User Registered Successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                userService.login(request));
    }
}