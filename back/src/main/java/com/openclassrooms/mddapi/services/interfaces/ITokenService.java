package com.openclassrooms.mddapi.services.interfaces;

import org.springframework.security.core.Authentication;

public interface ITokenService {
    /**
     * Generates a JSON Web Token (JWT) based on the provided authentication information.
     *
     * @param authentication The authentication object containing the user's details and authorities.
     * @return The generated JWT as a string.
     */
    String generateJwt(Authentication authentication);
}
