package com.openclassrooms.mddapi.exceptions;

import java.io.Serial;

public class ArticleNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public ArticleNotFoundException(String message) {
        super(message);
    }
}