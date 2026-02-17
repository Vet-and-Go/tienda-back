package com.grupo4.VetAndGo.persistence.repository.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.TokenUtilsJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenUtilsRepositoryImplTest {

    @Mock
    private TokenUtilsJpaDao tokenJpaDao;

    @InjectMocks
    private TokenUtilsRepositoryImpl tokenUtilsRepository;

    @Test
    void createSessionToken_ShouldDelegate() {
        when(tokenJpaDao.createSessionToken(1L)).thenReturn("token");
        assertEquals("token", tokenUtilsRepository.createSessionToken(1L));
        verify(tokenJpaDao).createSessionToken(1L);
    }

    @Test
    void getUserFromToken_ShouldDelegate() {
        UserJpaEntity user = new UserJpaEntity();
        when(tokenJpaDao.getUserFromToken("token")).thenReturn(user);
        assertSame(user, tokenUtilsRepository.getUserFromToken("token"));
        verify(tokenJpaDao).getUserFromToken("token");
    }

    @Test
    void deleteToken_ShouldDelegate() {
        tokenUtilsRepository.deleteToken(1L);
        verify(tokenJpaDao).deletesessionToken(1L);
    }
}
