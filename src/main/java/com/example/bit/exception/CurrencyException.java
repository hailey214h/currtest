package com.example.bit.exception;

public class CurrencyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CurrencyException(String message) {

        super("Currency Code " + message + " is not found.");
    }

//    public CurrencyException(String message, String type) {
//
//        super("Currency Code " + message + " data can't " + type);
//    }
}
