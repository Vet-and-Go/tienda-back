package com.grupo4.VetAndGo.domain.infrastructura;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderImplTest {

    private final PasswordEncoderImpl passwordEncoder = new PasswordEncoderImpl();

    @Test
    void encode_ShouldReturnEncodedPassword() {
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        // BCrypt hash structure usually starts with $2a$ or similar
        assertTrue(encodedPassword.startsWith("$2a$") || encodedPassword.startsWith("$2b$") || encodedPassword.startsWith("$2y$"));
    }

    @Test
    void verify_ShouldReturnTrue_WhenPasswordMatches() {
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        boolean result = passwordEncoder.verify(rawPassword, encodedPassword);

        assertTrue(result);
    }

    @Test
    void verify_ShouldReturnFalse_WhenPasswordDoesNotMatch() {
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        boolean result = passwordEncoder.verify("wrongPassword", encodedPassword);

        assertFalse(result);
    }
}
