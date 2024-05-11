package com.openclassrooms.mddapi.exceptions;

import java.io.Serial;

public class TopicNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public TopicNotFoundException(String message) {
        super(message);
    }
}