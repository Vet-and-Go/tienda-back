package com.grupo4.VetAndGo.persistence.repository;

import com.grupo4.VetAndGo.persistence.dao.jpa.UserJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import com.grupo4.VetAndGo.persistence.repository.impl.UserRepositoryImpl;
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
    void testFindAll() {
        // Arrange
        UserJpaEntity user1 = new UserJpaEntity(1L, "user1", "pass1", null);
        when(userJpaDao.findAll()).thenReturn(List.of(user1));

        // Act
        List<UserJpaEntity> result = userRepository.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUsername());
        verify(userJpaDao).findAll();
    }

    @Test
    void testFindById_Found() {
        // Arrange
        UserJpaEntity user = new UserJpaEntity(1L, "user1", "pass1", null);
        when(userJpaDao.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserJpaEntity result = userRepository.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userJpaDao).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(userJpaDao.findById(1L)).thenReturn(Optional.empty());

        // Act
        UserJpaEntity result = userRepository.findById(1L);

        // Assert
        assertNull(result);
        verify(userJpaDao).findById(1L);
    }

    @Test
    void testFindByUsername() {
        // Arrange
        UserJpaEntity user = new UserJpaEntity(1L, "user1", "pass1", null);
        when(userJpaDao.findByUsername("user1")).thenReturn(Optional.of(user));

        // Act
        Optional<UserJpaEntity> result = userRepository.findByUsername("user1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getUsername());
        verify(userJpaDao).findByUsername("user1");
    }

    @Test
    void testSave_NewUser() {
        // Arrange
        UserJpaEntity newUser = new UserJpaEntity(null, "newUser", "pass", null);
        UserJpaEntity savedUser = new UserJpaEntity(1L, "newUser", "pass", null);
        when(userJpaDao.insert(newUser)).thenReturn(savedUser);

        // Act
        UserJpaEntity result = userRepository.save(newUser);

        // Assert
        assertEquals(1L, result.getId());
        verify(userJpaDao).insert(newUser);
        verify(userJpaDao, never()).update(any());
    }

    @Test
    void testSave_ExistingUser() {
        // Arrange
        UserJpaEntity existingUser = new UserJpaEntity(1L, "user", "pass", null);
        when(userJpaDao.update(existingUser)).thenReturn(existingUser);

        // Act
        UserJpaEntity result = userRepository.save(existingUser);

        // Assert
        assertEquals(1L, result.getId());
        verify(userJpaDao).update(existingUser);
        verify(userJpaDao, never()).insert(any());
    }

    @Test
    void testDelete() {
        // Arrange
        doNothing().when(userJpaDao).deleteById(1L);

        // Act
        userRepository.delete(1L);

        // Assert
        verify(userJpaDao).deleteById(1L);
    }
}
