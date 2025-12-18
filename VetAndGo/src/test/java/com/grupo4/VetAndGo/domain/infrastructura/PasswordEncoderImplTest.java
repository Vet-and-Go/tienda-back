package com.grupo4.VetAndGo.domain.infrastructura;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderImplTest {

    private final PasswordEncoderImpl passwordEncoder = new PasswordEncoderImpl();

    @Test
    void testEncode() {
        // Arrange
        String rawPassword = "password123";

        // Act
        String encoded = passwordEncoder.encode(rawPassword);

        // Assert
        assertNotNull(encoded);
        assertNotEquals(rawPassword, encoded);
        assertTrue(encoded.startsWith("$2a$")); // Validating BCrypt prefix
    }

    @Test
    void testVerify_Success() {
        // Arrange
        String rawPassword = "password123";
        String encoded = passwordEncoder.encode(rawPassword);

        // Act
        boolean result = passwordEncoder.verify(rawPassword, encoded);

        // Assert
        assertTrue(result);
    }

    @Test
    void testVerify_Failure() {
        // Arrange
        String rawPassword = "password123";
        String encoded = passwordEncoder.encode(rawPassword);

        // Act
        boolean result = passwordEncoder.verify("wrongPassword", encoded);

        // Assert
        assertFalse(result);
    }
}
