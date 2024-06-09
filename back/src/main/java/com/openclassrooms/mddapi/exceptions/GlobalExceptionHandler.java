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
    /**
     * Handles MethodArgumentNotValidException, which occurs when an argument passed to a method is not valid.
     *
     * @param ex The MethodArgumentNotValidException instance
     * @return A ResponseEntity containing a DefaultResponseDto with a formatted error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto(formatErrorMessage(ex.getMessage())), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles AuthenticationException, which occurs when authentication fails.
     *
     * @param ex The AuthenticationException instance
     * @return A ResponseEntity containing a DefaultResponseDto with the error message "Authentication failed." and HTTP status UNAUTHORIZED
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<DefaultResponseDto> handleAuthenticationExceptions(AuthenticationException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Authentication failed."), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles ConstraintViolationException, which occurs when a constraint is violated.
     *
     * @param ex The ConstraintViolationException instance
     * @return A ResponseEntity containing a DefaultResponseDto with a formatted error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DefaultResponseDto> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto(formatErrorMessage(ex.getMessage())), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ResourceNotFoundException, which occurs when a requested resource is not found.
     *
     * @param ex The ResourceNotFoundException instance
     * @return A ResponseEntity containing a DefaultResponseDto with a formatted error message and HTTP status NOT_FOUND
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DefaultResponseDto> handleResourceNotFoundExceptions(ResourceNotFoundException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto(formatErrorMessage(ex.getMessage())), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles BadCredentialsException, which occurs when the provided credentials are invalid.
     *
     * @param ex The BadCredentialsException instance
     * @return A ResponseEntity containing a DefaultResponseDto with the error message "Identifiants inconnus." and HTTP status UNAUTHORIZED
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<DefaultResponseDto> handleBadCredentialsExceptions(BadCredentialsException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Identifiants inconnus."), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles NumberFormatException, which occurs when a String cannot be parsed as a Long.
     *
     * @param ex The NumberFormatException instance
     * @return A ResponseEntity containing a DefaultResponseDto with the error message "Can't parse the given String to Long." and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<DefaultResponseDto> handleNumberFormatExceptions(NumberFormatException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Can't parse the given String to Long."), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UserNotFoundException, which occurs when a requested user is not found.
     *
     * @param ex The UserNotFoundException instance
     * @return A ResponseEntity containing a DefaultResponseDto with the error message "User not found." and HTTP status UNAUTHORIZED
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<DefaultResponseDto> handleUserNotFoundExceptions(UserNotFoundException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("User not found."), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles EmailAlreadyUsedException, which occurs when the provided email is already in use.
     *
     * @param ex The EmailAlreadyUsedException instance
     * @return A ResponseEntity containing a DefaultResponseDto with the error message "This Email is already used." and HTTP status UNAUTHORIZED
     */
    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<DefaultResponseDto> handleUserNotFoundExceptions(EmailAlreadyUsedException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("This Email is already used."), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles RoleNotFoundException, which occurs when a requested role is not found.
     *
     * @param ex The RoleNotFoundException instance
     * @return A ResponseEntity containing a DefaultResponseDto with the error message "Role not found." and HTTP status NOT_FOUND
     */
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<DefaultResponseDto> handleRoleNotFoundExceptions(RoleNotFoundException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Role not found."), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles TopicNotFoundException, which occurs when a requested topic is not found.
     *
     * @param ex The TopicNotFoundException instance
     * @return A ResponseEntity containing a DefaultResponseDto with the error message "Topic not found." and HTTP status NOT_FOUND
     */
    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<DefaultResponseDto> handleTopicNotFoundExceptions(TopicNotFoundException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Topic not found."), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles ArticleNotFoundException, which occurs when a requested article is not found.
     *
     * @param ex The ArticleNotFoundException instance
     * @return A ResponseEntity containing a DefaultResponseDto with the error message "Article not found." and HTTP status NOT_FOUND
     */
    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<DefaultResponseDto> handleTopicNotFoundExceptions(ArticleNotFoundException ex) {
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Article not found."), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles IllegalArgumentException, which occurs when an illegal argument is passed to a method.
     *
     * @param ex The IllegalArgumentException instance
     * @return A ResponseEntity containing a DefaultResponseDto with the error message "Illegal argument." and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DefaultResponseDto> handleIllegalArgumentExceptions(IllegalArgumentException ex){
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Illegal argument."), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles TokenGenerationFailureException, which occurs when a JWT token cannot be generated for a user.
     *
     * @param ex The TokenGenerationFailureException instance
     * @return A ResponseEntity containing a DefaultResponseDto with the error message "Can't generate a JWT for this user." and HTTP status UNAUTHORIZED
     */
    @ExceptionHandler(TokenGenerationFailureException.class)
    public ResponseEntity<DefaultResponseDto> handleIllegalArgumentExceptions(TokenGenerationFailureException ex){
        return new ResponseEntity<DefaultResponseDto>(new DefaultResponseDto("Can't generate a JWT for this user."), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Formats the raw error message by extracting the relevant part and removing any trailing whitespace.
     *
     * @param rawErrorMessage The raw error message
     * @return The formatted error message
     */
    private String formatErrorMessage(String rawErrorMessage){
        return rawErrorMessage.substring(rawErrorMessage.lastIndexOf("[")+1).replace("]]", "").stripTrailing();
    }
}