package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.interfaces.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService, IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     * Retrieves a user by their unique identifier.
     */
    public User getById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Target user cannot be found."));
    }

    /**
     * {@inheritDoc}
     * Retrieves a user by their email address.
     */
    public User getByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Target user cannot be found."));
    }

    /**
     * {@inheritDoc}
     * Saves a new user or updates an existing one.
     */
    public User save(User user){
        return userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not valid."));
    }
}
