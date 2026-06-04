package com.allianz.insurance.exception;

public class PolicyNotFoundException extends RuntimeException {

    public PolicyNotFoundException(String message) {
        super(message);
    }
}