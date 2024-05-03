package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;

public interface IUserService {
    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return the user with the specified ID
     * @throws UserNotFoundException if the user is not found
     */
    User getById(Long id);

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user
     * @return the user with the specified email address
     * @throws UserNotFoundException if the user is not found
     */
    User getByEmail(String email);

    /**
     * Saves a new user or updates an existing one.
     *
     * @param user the user to be saved or updated
     * @return the saved or updated user
     */
    User save(User user);
}
