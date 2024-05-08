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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

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
    public ResponseEntity<?> getCredentials(Principal principal){
        User loggedUser = userService.getByEmail(principal.getName());
        return ResponseEntity.ok().body(new UserEmailUsernameDto(loggedUser));
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginPayloadDto loginRequest) {
        String emailOrUsername = loginRequest.getEmailOrUsername();
        String password = loginRequest.getPassword();
        String jwtToken = authService.login(emailOrUsername, password);
        if(Objects.equals(jwtToken, "")) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String email = "email";
        JwtResponseDto jwtResponse = JwtResponseDto.builder()
                .email(email)
                .token(jwtToken)
                .username("username")
                .build();
        return ResponseEntity.ok().body(jwtResponse);
    }

    @PostMapping("auth/register")
    public ResponseEntity<?> login(@Valid @RequestBody RegisterPayloadDto registerRequest) {
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        String username = registerRequest.getUsername();
        String jwtToken = authService.register(email, username, password);
        if(Objects.equals(jwtToken, "")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        JwtResponseDto jwtResponse = JwtResponseDto.builder().email(email).token(jwtToken).username(username).build();
        return ResponseEntity.ok().body(jwtResponse);
    }

    @PutMapping("auth/newcredentials")
    public ResponseEntity<?> updateCredentials(@Valid @RequestBody UpdateCredentialsPayloadDto updateCredentialsRequest,
                                               Principal principal) {
        // !!! move to service
        String newEmail = updateCredentialsRequest.getEmail();
        String newUsername = updateCredentialsRequest.getUsername();
        String loggedUserEmail = principal.getName();
        User user = userService.getByEmail(loggedUserEmail);

        user.setEmail(newEmail);
        user.setName(newUsername);
        System.out.println(user);
        userService.save(user);
        return ResponseEntity.ok().build();
    }

}
