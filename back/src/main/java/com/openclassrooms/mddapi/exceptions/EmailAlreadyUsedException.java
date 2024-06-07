package com.openclassrooms.mddapi.exceptions;

import java.io.Serial;

public class EmailAlreadyUsedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}