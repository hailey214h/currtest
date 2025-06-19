package com.example.bit.exception;

public class CurrencyAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CurrencyAlreadyExistsException(String message) {
        super("Currency Code " + message + " is already exists.");
    }
}
