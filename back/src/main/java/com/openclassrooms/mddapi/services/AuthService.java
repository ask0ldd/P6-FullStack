package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.*;
import com.openclassrooms.mddapi.models.Role;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.RoleRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.interfaces.IAuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AuthService implements IAuthService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public AuthService(TokenService tokenService,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            UserRepository userRepository) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     * Logs in a user using their email or username and password.
     */
    public String login(String emailOrUsername, String password) {
        // get the email out of the parameter
        String email = parseEmail(emailOrUsername);
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = tokenService.generateJwt(auth);
        if (Objects.equals(token, ""))
            throw new TokenGenerationFailureException("Token generation failed.");
        return token;
    }

    /**
     * {@inheritDoc}
     * Registers a new user with the provided email, username, and password.
     */
    public String register(String email, String username, String password) { // !!! should check constraints
        createNewUser(email, username, password);
        // try to authenticate the user using email and password
        // Authentication : Set by an AuthenticationManager to indicate the authorities
        // that the principal has been granted
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        // produces a JWT based on the Authentication produced for the authenticated
        // user
        String token = tokenService.generateJwt(auth);
        if (Objects.equals(token, ""))
            throw new TokenGenerationFailureException("Token generation failed.");
        return token;
    }

    /**
     * {@inheritDoc}
     * Updates the credentials of a user with the provided email and/or username.
     */
    public void updateCredentials(String currentEmail, String newEmail, String newUsername) {
        // check if the new email is not already known and used by another user
        if ((userRepository.existsByEmail(newEmail)) && (!Objects.equals(currentEmail, newEmail))) {
            throw new EmailAlreadyUsedException("The Email " + newEmail + " is already in use.");
        }

        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        user.setEmail(newEmail);
        user.setName(newUsername);
        userRepository.save(user);
    }

    /**
     * Parses the email from the provided email or username.
     * 
     * @param emailOrUsername the email or username to parse
     * @return the parsed email
     * @throws BadCredentialsException if the email or username is not found
     */
    private String parseEmail(String emailOrUsername) {
        // check if the user can be found using the parameter as an email
        Optional<User> user = userRepository.findByEmail(emailOrUsername);
        if (user.isPresent()) {
            return user.get().getEmail();
        }

        // check if the user can be found using the parameter as a username
        return userRepository.findByName(emailOrUsername)
                .map(User::getEmail)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials."));
    }

    /**
     * Creates a new user with the provided email, username, and password.
     * 
     * @param email    the email of the new user
     * @param username the username of the new user
     * @param password the password of the new user
     * @throws RoleNotFoundException if the user role is not found
     * @throws BadRequestException   if an exception occurs during user creation
     */
    private void createNewUser(String email, String username, String password) {
        try {
            // the password has to be encoded before any insertion into the DB
            String encodedPassword = passwordEncoder.encode(password);

            // Assigning a role by default to the new user : USER
            Role userRole = roleRepository.findByAuthority("USER")
                    .orElseThrow(() -> new RoleNotFoundException("User role not found"));
            Set<Role> authorities = new HashSet<>();
            authorities.add(userRole);

            User user = User.builder().name(username).email(email).password(encodedPassword).authorities(authorities)
                    .build();
            userRepository.save(user);
        } catch (RoleNotFoundException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e + "\u001B[0m");
            throw new BadRequestException();
        } // converting all the exceptions beside the roleNotFound one into badRequestExceptions
    }
}
