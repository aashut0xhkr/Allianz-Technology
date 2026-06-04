package com.allianz.insurance.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PolicyNotFoundException.class)
    public ResponseEntity<String> handlePolicyNotFound(
            PolicyNotFoundException ex) {

        log.warn("Policy not found: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFound(
            CustomerNotFoundException ex) {

        log.warn("Customer not found: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<String> handleCustomerAlreadyExists(
            CustomerAlreadyExistsException ex) {

        log.warn("Customer already exists: {}",
                ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(
            UserAlreadyExistsException ex) {

        log.warn("Business exception occurred: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(
            InvalidCredentialsException ex) {

        log.warn("Authentication failed: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .body(errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(
            AccessDeniedException ex) {

        log.warn("Access denied: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Access Denied");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(
            Exception ex) {

        log.error("Unexpected exception occurred", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong");
    }
}