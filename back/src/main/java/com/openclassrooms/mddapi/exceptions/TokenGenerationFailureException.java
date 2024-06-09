package com.openclassrooms.mddapi.exceptions;

public class TokenGenerationFailureException extends RuntimeException {
    public TokenGenerationFailureException(String message) {
        super(message);
    }
}