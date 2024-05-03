package com.openclassrooms.mddapi.services.interfaces;

public interface IAuthService {

    /**
     * Logs in a user with the provided email or username and password.
     *
     * @param emailOrUsername the email or username of the user
     * @param password the password of the user
     * @return a session token or an error message if the login fails
     */
    public String login(String emailOrUsername, String password);

    /**
     * Registers a new user with the provided email, username, and password.
     *
     * @param email the email of the new user
     * @param username the username of the new user
     * @param password the password of the new user
     * @return a session token or an error message if the registration fails
     */
    public String register(String email, String username, String password);

}
