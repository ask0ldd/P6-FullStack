package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.payloads.LoginPayloadDto;
import com.openclassrooms.mddapi.dto.payloads.RegisterPayloadDto;
import com.openclassrooms.mddapi.dto.payloads.UpdateCredentialsPayloadDto;
import com.openclassrooms.mddapi.dto.reponses.JwtResponseDto;
import com.openclassrooms.mddapi.dto.reponses.UserEmailUsernameDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.AuthService;
import com.openclassrooms.mddapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService){
        this.authService = authService;
        this.userService = userService;
    }

    /**
     * Retrieves the credentials (email and username) of the currently logged-in user.
     *
     * @param principal the {@link Principal} object representing the authenticated user
     * @return a ResponseEntity containing a UserEmailUsernameDto object with the user's email and username
     */
    @GetMapping("auth/credentials")
    public ResponseEntity<UserEmailUsernameDto> getCredentials(Principal principal){
        User loggedUser = userService.getByEmail(principal.getName());
        return ResponseEntity.ok().body(new UserEmailUsernameDto(loggedUser));
    }

    /**
     * Authenticates a user and generates a JWT token upon successful login.
     *
     * @param loginRequest the login request containing the email or username and password
     * @return a ResponseEntity containing a JwtResponseDto with the generated JWT token, email, and username
     */
    @PostMapping("auth/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginPayloadDto loginRequest) {
        String emailOrUsername = loginRequest.getEmailOrUsername();
        String password = loginRequest.getPassword();
        String jwtToken = authService.login(emailOrUsername, password);
        String email = "email";
        JwtResponseDto jwtResponse = JwtResponseDto.builder()
                .email(email)
                .token(jwtToken)
                .username("username")
                .build();
        return ResponseEntity.ok().body(jwtResponse);
    }

    /**
     * Registers a new user and generates a JWT token.
     *
     * @param registerRequest the registration request payload containing email, password, and username
     * @return a ResponseEntity containing a JwtResponseDto with the user's email, generated JWT token, and username
     */
    @PostMapping("auth/register")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody RegisterPayloadDto registerRequest) {
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        String username = registerRequest.getUsername();
        String jwtToken = authService.register(email, username, password);
        JwtResponseDto jwtResponse = JwtResponseDto.builder().email(email).token(jwtToken).username(username).build();
        return ResponseEntity.ok().body(jwtResponse);
    }

    /**
     * Updates the user's credentials (email and username) in the system.
     *
     * @param updateCredentialsRequest the request payload containing the new email and username
     * @param principal the authenticated user's principal, used to retrieve the current email
     * @return A ResponseEntity with an empty body and a status code of 200.
     * if the update was successful
     */
    @PutMapping("auth/newcredentials")
    public ResponseEntity<Void> updateCredentials(@Valid @RequestBody UpdateCredentialsPayloadDto updateCredentialsRequest,
                                               Principal principal) {
        String currentEmail = principal.getName();
        String newEmail = updateCredentialsRequest.getEmail();
        String newUsername = updateCredentialsRequest.getUsername();
        authService.updateCredentials(currentEmail, newEmail, newUsername);
        return ResponseEntity.ok().build();
    }

}
