package com.openclassrooms.mddapi.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.openclassrooms.mddapi.utils.KeyGeneratorUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtConfiguration {

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    // Generating a pair of keys
    public JwtConfiguration() {
        KeyPair rsaKeys = KeyGeneratorUtility.generateRSAKeys();
        this.publicKey = (RSAPublicKey) rsaKeys.getPublic();
        this.privateKey = (RSAPrivateKey) rsaKeys.getPrivate();
    }

    /**
     * Creates and returns a JwtDecoder bean that can be used to decode and
     * validate JSON Web Tokens (JWTs) using the provided public key.
     * @return a {@link JwtDecoder} instance configured with the provided public key
     */
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    /**
     * Creates a {@link JwtEncoder} bean using the provided public and private keys.
     * The encoder is based on the Nimbus JOSE + JWT implementation and uses an RSA key pair.
     * @return a configured {@link JwtEncoder} instance
     */
    // configuring the encoder used to sign the JWT
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Creates a {@link JwtAuthenticationConverter} bean with a custom {@link JwtGrantedAuthoritiesConverter}.
     * The converter maps the "roles" claim to granted authorities with the "ROLE_" prefix.
     * @return the configured {@link JwtAuthenticationConverter} instance
     */
    // Adding a prefix to the roles claim contained into the JWT.
    // Ex : JWT Role : "USER" -> "ROLE_USER".
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }
}
