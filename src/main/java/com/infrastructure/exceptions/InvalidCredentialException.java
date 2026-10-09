package com.infrastructure.exceptions;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException() {
        super("Nome e/ou senha incorretos.");
    }
}
