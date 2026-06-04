package com.allianz.insurance.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void shouldHandleUserAlreadyExistsException() {

        ResponseEntity<String> response =
                handler.handleUserAlreadyExists(
                        new UserAlreadyExistsException(
                                "User Exists"));

        assertEquals(
                409,
                response.getStatusCode().value());
    }
}