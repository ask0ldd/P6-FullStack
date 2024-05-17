package com.openclassrooms.mddapi.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyGeneratorUtility {

    /**
     * Generates a pair of RSA public and private keys with a key length of 2048 bits.
     *
     * @return A {@link KeyPair} object containing the generated RSA public and private keys.
     * @throws IllegalStateException If an error occurs during the key generation process.
     */
    public static KeyPair generateRSAKeys() {

        KeyPair keyPair;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();

        } catch (Exception e) {
            throw new IllegalStateException("Can't generate RSA Key Pair.");
        }

        return keyPair;
    }
}
