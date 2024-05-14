package com.openclassrooms.mddapi.exceptions;

import java.io.Serial;

public class TokenGenerationFailureException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public TokenGenerationFailureException(String message) {
        super(message);
    }
}