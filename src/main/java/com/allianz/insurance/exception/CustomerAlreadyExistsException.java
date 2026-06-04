package com.allianz.insurance.exception;

public class CustomerAlreadyExistsException
        extends RuntimeException {

    public CustomerAlreadyExistsException(
            String message) {
        super(message);
    }
}