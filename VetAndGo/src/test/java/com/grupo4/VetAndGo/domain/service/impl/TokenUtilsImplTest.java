package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.exception.ValidationException;
import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.domain.model.User;
import com.grupo4.VetAndGo.domain.repository.TokenUtilsRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
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
    private TokenUtilsImpl tokenUtils;

    private UserJpaEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserJpaEntity(1L, "username", "password", Role.USER);
    }

    @Test
    void getUserFromToken_ShouldReturnUser_WhenTokenIsValid() {
        when(tokenUtilsRepository.getUserFromToken("validToken")).thenReturn(userEntity);

        User result = tokenUtils.getUserbFromToken("validToken");

        assertNotNull(result);
        assertEquals(userEntity.getUsername(), result.getUsername());
        verify(tokenUtilsRepository, times(1)).getUserFromToken("validToken");
    }

    @Test
    void getUserFromToken_ShouldThrowValidationException_WhenTokenIsInvalid() {
        when(tokenUtilsRepository.getUserFromToken("invalidToken")).thenReturn(null);

        assertThrows(ValidationException.class, () -> tokenUtils.getUserbFromToken("invalidToken"));
        verify(tokenUtilsRepository, times(1)).getUserFromToken("invalidToken");
    }

    @Test
    void validateToken_ShouldReturnUser_WhenTokenIsValid() {
        when(tokenUtilsRepository.getUserFromToken("validToken")).thenReturn(userEntity);

        User result = tokenUtils.validateToken("validToken");

        assertNotNull(result);
        assertEquals(userEntity.getUsername(), result.getUsername());
        verify(tokenUtilsRepository, times(1)).getUserFromToken("validToken");
    }

    @Test
    void validateToken_ShouldThrowValidationException_WhenTokenIsInvalid() {
        when(tokenUtilsRepository.getUserFromToken("invalidToken")).thenReturn(null);

        assertThrows(ValidationException.class, () -> tokenUtils.validateToken("invalidToken"));
        verify(tokenUtilsRepository, times(1)).getUserFromToken("invalidToken");
    }

    @Test
    void deleteToken_ShouldDeleteToken_WhenTokenIsValid() {
        when(tokenUtilsRepository.getUserFromToken("validToken")).thenReturn(userEntity);
        // Repository deleteToken usually returns void or int? Checking TokenUtilsRepository later but assume void for now as per TokenUtilsImpl
        // The mock should handle void.
        // But checking TokenUtilsImpl: tokenUtilsRepository.deleteToken(user.getId());
        // So I need to verify that call.
        
        tokenUtils.deleteToken("validToken");

        verify(tokenUtilsRepository, times(1)).getUserFromToken("validToken");
        verify(tokenUtilsRepository, times(1)).deleteToken(1L);
    }

    @Test
    void deleteToken_ShouldThrowValidationException_WhenTokenIsInvalid() {
        when(tokenUtilsRepository.getUserFromToken("invalidToken")).thenReturn(null);

        assertThrows(ValidationException.class, () -> tokenUtils.deleteToken("invalidToken"));
        verify(tokenUtilsRepository, times(1)).getUserFromToken("invalidToken");
        verify(tokenUtilsRepository, never()).deleteToken(anyLong());
    }

}
