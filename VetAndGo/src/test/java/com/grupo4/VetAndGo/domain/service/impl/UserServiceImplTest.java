package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.dto.LoginDto;
import com.grupo4.VetAndGo.domain.dto.UserDto;
import com.grupo4.VetAndGo.domain.exception.ValidationException;
import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.domain.repository.UserRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.grupo4.VetAndGo.domain.exception.ResourceNotFoundException;
import com.grupo4.VetAndGo.domain.repository.TokenUtilsRepository;
import com.grupo4.VetAndGo.domain.service.PasswordEncoderService;
import java.util.List;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoderService passwordEncoderService;
    @Mock
    private TokenUtilsRepository tokenUtilsRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void testGetAll() {
        // Arrange
        UserJpaEntity user1 = new UserJpaEntity(1L, "user1", "pass1", Role.ADMIN);
        UserJpaEntity user2 = new UserJpaEntity(2L, "user2", "pass2", Role.USER);
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act
        List<UserDto> result = userServiceImpl.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).username());
        assertEquals("user2", result.get(1).username());
    }

    @Test
    void testGetById_Success() {
        // Arrange
        UserJpaEntity user = new UserJpaEntity(1L, "user1", "pass1", Role.ADMIN);
        when(userRepository.findById(1L)).thenReturn(user);

        // Act
        UserDto result = userServiceImpl.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("user1", result.username());
    }

    @Test
    void testGetById_NotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImpl.getById(1L));
    }

    @Test
    void testFindByUsername_Success() {
        // Arrange
        UserJpaEntity user = new UserJpaEntity(1L, "user1", "pass1", Role.ADMIN);
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));

        // Act
        UserDto result = userServiceImpl.findByUsername("user1");

        // Assert
        assertNotNull(result);
        assertEquals("user1", result.username());
    }

    @Test
    void testCreate() {
        // Arrange
        UserDto input = new UserDto(null, "user1", "plainPass", Role.USER);
        UserJpaEntity saved = new UserJpaEntity(1L, "user1", "encodedPass", Role.USER);

        when(passwordEncoderService.encode("plainPass")).thenReturn("encodedPass");
        when(userRepository.save(any(UserJpaEntity.class))).thenReturn(saved);

        // Act
        UserDto result = userServiceImpl.create(input);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("user1", result.username());
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        UserDto updateInput = new UserDto(1L, "newUser", "newPass", Role.ADMIN);
        UserJpaEntity existing = new UserJpaEntity(1L, "oldUser", "oldPass",
                Role.USER);
        UserJpaEntity updated = new UserJpaEntity(1L, "newUser", "newEncoded",
                Role.ADMIN);

        when(userRepository.findById(1L)).thenReturn(existing);
        when(passwordEncoderService.encode("newPass")).thenReturn("newEncoded");
        when(userRepository.save(any(UserJpaEntity.class))).thenReturn(updated);

        // Act
        UserDto result = userServiceImpl.update(1L, updateInput);

        // Assert
        assertEquals("newUser", result.username());
        assertEquals(Role.ADMIN, result.role());
    }

    @Test
    void testDelete_Success() {
        // Arrange
        UserJpaEntity existing = new UserJpaEntity(1L, "user1", "pass1", Role.USER);
        when(userRepository.findById(1L)).thenReturn(existing);

        // Act
        userServiceImpl.delete(1L);

        // Assert
        verify(userRepository).delete(1L);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        LoginDto loginDto = new LoginDto("user1", "pass1", null);
        UserJpaEntity user = new UserJpaEntity(1L, "user1", "encodedPass", Role.USER);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(passwordEncoderService.verify("pass1", "encodedPass")).thenReturn(true);
        when(tokenUtilsRepository.createSessionToken(1L)).thenReturn("validToken");

        // Act
        String token = userServiceImpl.login(loginDto);

        // Assert
        assertEquals("validToken", token);
    }

    @Test
    void testLogin_WrongPassword() {
        // Arrange
        LoginDto loginDto = new LoginDto("user1", "wrongPass", null);
        UserJpaEntity user = new UserJpaEntity(1L, "user1", "encodedPass", Role.USER);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(passwordEncoderService.verify("wrongPass", "encodedPass")).thenReturn(false);

        // Act & Assert
        assertThrows(ValidationException.class, () -> userServiceImpl.login(loginDto));
    }

    @Test
    void testLogout_Success() {
        // Arrange
        LoginDto loginDto = new LoginDto("user1", null, null);
        UserJpaEntity user = new UserJpaEntity(1L, "user1", "pass1", Role.USER);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));

        // Act
        userServiceImpl.logout(loginDto);

        // Assert
        verify(userRepository).findByUsername("user1");
        verify(tokenUtilsRepository).deleteToken(1L);
    }

    @Test
    void testLogout_UserNotFound() {
        // Arrange
        LoginDto loginDto = new LoginDto("unknownUser", null, null);

        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userServiceImpl.logout(loginDto));
        assertEquals("User unknownUser not found.", exception.getMessage());
        verify(tokenUtilsRepository, never())
                .deleteToken(anyLong());
    }

}
