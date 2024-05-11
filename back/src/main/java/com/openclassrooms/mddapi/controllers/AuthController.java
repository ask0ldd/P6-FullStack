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

    @GetMapping("auth/credentials")
    public ResponseEntity<UserEmailUsernameDto> getCredentials(Principal principal){
        User loggedUser = userService.getByEmail(principal.getName());
        return ResponseEntity.ok().body(new UserEmailUsernameDto(loggedUser));
    }

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

    @PostMapping("auth/register")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody RegisterPayloadDto registerRequest) {
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        String username = registerRequest.getUsername();
        String jwtToken = authService.register(email, username, password);
        JwtResponseDto jwtResponse = JwtResponseDto.builder().email(email).token(jwtToken).username(username).build();
        return ResponseEntity.ok().body(jwtResponse);
    }

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
