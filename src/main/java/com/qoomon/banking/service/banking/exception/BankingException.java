package com.qoomon.banking.service.banking.exception;

public class BankingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BankingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankingException(String message) {
        super(message);
    }
}
