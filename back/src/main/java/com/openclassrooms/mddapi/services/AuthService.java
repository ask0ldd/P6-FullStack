package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.Role;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.RoleRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public AuthService(TokenService tokenService,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository,
                       UserRepository userRepository)
    {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public String login(String emailOrUsername, String password){
        try{
            String email = parseUserEmail(emailOrUsername);
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return tokenService.generateJwt(auth);
        } catch(AuthenticationException e){
            // !!!
            return null;
        }
    }

    // !!! should check constraints and should check username has no @ in it
    public String register(String email, String username, String password) {
        try {
            createNewUser(email, username, password);
            // try to authenticate the user using email and password
            // Authentication : Set by an AuthenticationManager to indicate the authorities that the principal has been granted
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            // produces a JWT based on the Authentication produced for the authenticated user
            return tokenService.generateJwt(auth);
        } catch (AuthenticationException e) {
            // !!!
            return null;
        }
    }

    private String parseUserEmail(String emailOrUsername) {
        // if @ into field, then emailOrUsername is an email
        if (emailOrUsername.contains("@")) {
            return emailOrUsername;
        }
        // if no @, retrieve the user email from the DB
        return userRepository.findByUserName(emailOrUsername)
                .map(User::getEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    private void createNewUser(String email, String username, String password) {
        // the password  has to be encoded before any insertion into the DB
        String encodedPassword = passwordEncoder.encode(password);

        // Assigning a role by default to the new user : USER
        Role userRole = roleRepository.findByAuthority("USER").orElseThrow(() -> new RuntimeException("User role not found"));
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        User user = User.builder().userName(username).email(email).password(encodedPassword).authorities(authorities).build();
        userRepository.save(user);
    }
}
