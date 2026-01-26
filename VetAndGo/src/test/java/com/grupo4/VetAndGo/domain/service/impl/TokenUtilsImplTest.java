package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.exception.ValidationException;
import com.grupo4.VetAndGo.domain.model.User;
import com.grupo4.VetAndGo.domain.repository.TokenUtilsRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenUtilsImplTest {

    @Mock
    private TokenUtilsRepository tokenUtilsRepository;

    @InjectMocks
    private TokenUtilsImpl tokenUtilsImpl;

    @Test
    void testGetUserFromToken_Success() {
        // Arrange
        String token = "validToken";
        UserJpaEntity userEntity = new UserJpaEntity(1L, "user1", "pass1", com.grupo4.VetAndGo.domain.model.Role.USER);
        when(tokenUtilsRepository.getUserFromToken(token)).thenReturn(userEntity);

        // Act
        User result = tokenUtilsImpl.getUserbFromToken(token);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("user1", result.getUsername());
    }

    @Test
    void testGetUserFromToken_InvalidToken() {
        // Arrange
        String token = "invalidToken";
        when(tokenUtilsRepository.getUserFromToken(token)).thenReturn(null);

        // Act & Assert
        ValidationException exception = assertThrows(
                ValidationException.class, () -> {
                    tokenUtilsImpl.getUserbFromToken(token);
                });
        assertEquals("Invalid token.", exception.getMessage());
    }

    @Test
    void testDeleteToken_Success() {
        // Arrange
        String token = "validToken";
        UserJpaEntity userEntity = new UserJpaEntity(1L, "user1", "pass1", com.grupo4.VetAndGo.domain.model.Role.USER);
        when(tokenUtilsRepository.getUserFromToken(token)).thenReturn(userEntity);

        // Act
        tokenUtilsImpl.deleteToken(token);

        // Assert
        verify(tokenUtilsRepository, times(1)).deleteToken(1L);
    }

    @Test
    void testValidateToken_Valid() {
        // Arrange
        String token = "validToken";
        UserJpaEntity userEntity = new UserJpaEntity(1L, "user1", "pass1", com.grupo4.VetAndGo.domain.model.Role.USER);
        when(tokenUtilsRepository.getUserFromToken(token)).thenReturn(userEntity);

        // Act
        tokenUtilsImpl.validateToken(token);

        // Assert
        verify(tokenUtilsRepository, times(1)).getUserFromToken(token);
    }

    @Test
    void testValidateToken_Invalid() {
        // Arrange
        String token = "invalidToken";
        when(tokenUtilsRepository.getUserFromToken(token)).thenReturn(null);

        // Act & Assert
        ValidationException exception = assertThrows(
                ValidationException.class, () -> {
                    tokenUtilsImpl.validateToken(token);
                });
        assertEquals("Invalid token.", exception.getMessage());
    }
}
