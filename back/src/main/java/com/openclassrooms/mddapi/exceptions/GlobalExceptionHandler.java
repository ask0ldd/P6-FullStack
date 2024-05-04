package com.openclassrooms.mddapi.exceptions;

import com.openclassrooms.mddapi.dto.reponses.DefaultResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(new DefaultResponseDto(formatErrorMessage(ex.getMessage())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        return new ResponseEntity<>(new DefaultResponseDto(formatErrorMessage(ex.getMessage())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<Object> handleConstraintViolationExceptions(ArticleNotFoundException ex) {
        return new ResponseEntity<>(new DefaultResponseDto(formatErrorMessage(ex.getMessage())), HttpStatus.NOT_FOUND);
    }

    private String formatErrorMessage(String rawErrorMessage){
        return rawErrorMessage.substring(rawErrorMessage.lastIndexOf("[")+1).replace("]]", "").stripTrailing();
    }
}
