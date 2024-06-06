package com.openclassrooms.mddapi.exceptions;

import com.openclassrooms.mddapi.dto.reponses.DefaultResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto(formatErrorMessage(ex.getMessage())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<DefaultResponseDto> handleAuthenticationExceptions(AuthenticationException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Authentication failed."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DefaultResponseDto> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto(formatErrorMessage(ex.getMessage())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DefaultResponseDto> handleResourceNotFoundExceptions(ResourceNotFoundException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto(formatErrorMessage(ex.getMessage())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<DefaultResponseDto> handleBadCredentialsExceptions(BadCredentialsException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Identifiants inconnus."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<DefaultResponseDto> handleNumberFormatExceptions(NumberFormatException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Can't parse the given String to Long."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<DefaultResponseDto> handleUserNotFoundExceptions(UserNotFoundException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("User not found."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<DefaultResponseDto> handleRoleNotFoundExceptions(RoleNotFoundException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Role not found."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<DefaultResponseDto> handleTopicNotFoundExceptions(TopicNotFoundException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Topic not found."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<DefaultResponseDto> handleTopicNotFoundExceptions(ArticleNotFoundException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Article not found."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DefaultResponseDto> handleIllegalArgumentExceptions(IllegalArgumentException ex){
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Illegal argument."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenGenerationFailureException.class)
    public ResponseEntity<DefaultResponseDto> handleIllegalArgumentExceptions(TokenGenerationFailureException ex){
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Can't generate a JWT for this user."), HttpStatus.UNAUTHORIZED);
    }

    private String formatErrorMessage(String rawErrorMessage){
        return rawErrorMessage.substring(rawErrorMessage.lastIndexOf("[")+1).replace("]]", "").stripTrailing();
    }
}

// !!! deal with Long.parseLong(userId)); // throw NumberFormatException from topic controller (bottom)

/*
A DisabledException must be thrown if an account is disabled and the AuthenticationManager can test for this state.
A LockedException must be thrown if an account is locked and the AuthenticationManager can test for account locking.
A BadCredentialsException must be thrown if incorrect credentials are presented. Whilst the above exceptions are optional, an AuthenticationManager must always test credentials.

 */