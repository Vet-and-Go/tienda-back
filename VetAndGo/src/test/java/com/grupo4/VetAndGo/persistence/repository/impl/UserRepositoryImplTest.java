package com.grupo4.VetAndGo.persistence.repository.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.UserJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserJpaDao userJpaDao;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Test
    void findAll_ShouldDelegate() {
        when(userJpaDao.findAll()).thenReturn(List.of(new UserJpaEntity()));
        assertEquals(1, userRepository.findAll().size());
        verify(userJpaDao).findAll();
    }

    @Test
    void findById_ShouldDelegate() {
        UserJpaEntity user = new UserJpaEntity();
        when(userJpaDao.findById(1L)).thenReturn(Optional.of(user));
        assertSame(user, userRepository.findById(1L));
        verify(userJpaDao).findById(1L);
    }

    @Test
    void findById_ShouldReturnNull_WhenUserNotFound() {
        when(userJpaDao.findById(1L)).thenReturn(Optional.empty());
        assertNull(userRepository.findById(1L));
    }

    @Test
    void findByUsername_ShouldDelegate() {
        userRepository.findByUsername("user");
        verify(userJpaDao).findByUsername("user");
    }

    @Test
    void save_ShouldDelegateToInsert_WhenIdIsNull() {
        UserJpaEntity user = new UserJpaEntity();
        when(userJpaDao.insert(user)).thenReturn(user);

        userRepository.save(user);

        verify(userJpaDao).insert(user);
        verify(userJpaDao, never()).update(any());
    }

    @Test
    void save_ShouldDelegateToUpdate_WhenIdIsNotNull() {
        UserJpaEntity user = new UserJpaEntity();
        user.setId(1L);
        when(userJpaDao.update(user)).thenReturn(user);

        userRepository.save(user);

        verify(userJpaDao).update(user);
        verify(userJpaDao, never()).insert(any());
    }

    @Test
    void delete_ShouldDelegate() {
        userRepository.delete(1L);
        verify(userJpaDao).deleteById(1L);
    }
}
