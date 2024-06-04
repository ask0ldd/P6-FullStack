package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.exceptions.TokenGenerationFailureException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;

public interface IAuthService {

    /**
     * Logs in a user using their email or username and password.
     *
     * @param emailOrUsername the email or username of the user
     * @param password        the password of the user
     * @return the generated JWT token
     * @throws TokenGenerationFailureException if the token generation fails
     */
    public String login(String emailOrUsername, String password);

    /**
     * Registers a new user with the provided email, username, and password.
     *
     * @param email    the email of the new user
     * @param username the username of the new user
     * @param password the password of the new user
     * @return the generated JWT token
     * @throws TokenGenerationFailureException if the token generation fails
     */
    public String register(String email, String username, String password);

    /**
     * Updates the credentials of a user with the provided current email, new email,
     * and new username.
     *
     * @param currentEmail the current email of the user
     * @param newEmail     the new email of the user
     * @param newUsername  the new username of the user
     * @throws UserNotFoundException if the user is not found
     */
    public void updateCredentials(String currentEmail, String newEmail, String newUsername);
}
