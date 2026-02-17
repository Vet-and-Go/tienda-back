package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.dto.LoginDto;
import com.grupo4.VetAndGo.domain.dto.UserDto;
import com.grupo4.VetAndGo.domain.exception.BussinesException;
import com.grupo4.VetAndGo.domain.exception.ResourceNotFoundException;
import com.grupo4.VetAndGo.domain.exception.ValidationException;
import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.domain.repository.TokenUtilsRepository;
import com.grupo4.VetAndGo.domain.repository.UserRepository;
import com.grupo4.VetAndGo.domain.service.PasswordEncoderService;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private PasswordEncoderService passwordEncoderService;

    @Mock
    private TokenUtilsRepository tokenUtils;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserJpaEntity userEntity;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userEntity = new UserJpaEntity(1L, "username", "hashedPassword", Role.USER);
        userDto = new UserDto(1L, "username", "password", Role.USER);
    }

    @Test
    void getAll_ShouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        List<UserDto> result = userService.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(userEntity.getUsername(), result.get(0).username());
    }

    @Test
    void getById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(userEntity);

        UserDto result = userService.getById(1L);

        assertNotNull(result);
        assertEquals(userEntity.getUsername(), result.username());
    }

    @Test
    void getById_ShouldThrowResourceNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userService.getById(1L));
    }

    @Test
    void findByUsername_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(userEntity));

        UserDto result = userService.findByUsername("username");

        assertNotNull(result);
        assertEquals(userEntity.getUsername(), result.username());
    }

    @Test
    void findByUsername_ShouldThrowResourceNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findByUsername("username"));
    }

    @Test
    void create_ShouldReturnCreatedUser() {
        when(userRepository.save(any(UserJpaEntity.class))).thenAnswer(invocation -> {
            UserJpaEntity entity = invocation.getArgument(0);
            entity.setId(1L);
            return entity;
        });
        when(passwordEncoderService.encode("password")).thenReturn("hashedPassword");

        UserDto result = userService.create(new UserDto(null, "username", "password", Role.USER));

        assertNotNull(result);
        assertEquals("username", result.username());
    }

    @Test
    void create_ShouldThrowBusinessException_WhenUserAlreadyExists() {
        // Implementation check: if (userDto.id() != null && userRepository.findById(userDto.id()) != null)
        when(userRepository.findById(1L)).thenReturn(userEntity);

        assertThrows(BussinesException.class, () -> userService.create(userDto));
    }

    @Test
    void update_ShouldReturnUpdatedUser() {
        when(userRepository.findById(1L)).thenReturn(userEntity);
        // determinePassword check: userDto has "password", existing has "hashedPassword".
        // If they differ and new is not empty, re-encode.
        when(passwordEncoderService.encode("newPassword")).thenReturn("newHashedPassword");
        when(userRepository.save(any(UserJpaEntity.class))).thenReturn(userEntity);

        UserDto updateDto = new UserDto(1L, "newUsername", "newPassword", Role.ADMIN);
        
        UserDto result = userService.update(1L, updateDto);

        assertNotNull(result);
        verify(userRepository).save(any(UserJpaEntity.class));
    }

    @Test
    void update_ShouldThrowResourceNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userService.update(1L, userDto));
    }
    
    @Test
    void delete_ShouldDeleteUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(userEntity);
        doNothing().when(userRepository).delete(1L);
        
        userService.delete(1L);
        
        verify(userRepository).delete(1L);
    }

    @Test
    void delete_ShouldThrowResourceNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(null);
        
        assertThrows(ResourceNotFoundException.class, () -> userService.delete(1L));
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreCorrect() {
        LoginDto loginDto = new LoginDto("username", "password", null);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(userEntity));
        when(passwordEncoderService.verify("password", "hashedPassword")).thenReturn(true);
        when(tokenUtils.createSessionToken(1L)).thenReturn("token");

        String token = userService.login(loginDto);

        assertEquals("token", token);
    }

    @Test
    void login_ShouldThrowValidationException_WhenUserNotFound() {
        LoginDto loginDto = new LoginDto("username", "password", null);
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> userService.login(loginDto));
    }

    @Test
    void login_ShouldThrowValidationException_WhenPasswordIsIncorrect() {
        LoginDto loginDto = new LoginDto("username", "password", null);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(userEntity));
        when(passwordEncoderService.verify("password", "hashedPassword")).thenReturn(false);

        assertThrows(ValidationException.class, () -> userService.login(loginDto));
    }

    @Test
    void logout_ShouldDeleteToken_WhenUserExists() {
        LoginDto loginDto = new LoginDto("username", "password", null);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(userEntity));
        doNothing().when(tokenUtils).deleteToken(1L);

        userService.logout(loginDto);

        verify(tokenUtils).deleteToken(1L);
    }

    @Test
    void update_ShouldThrowValidationException_WhenRoleIsNull() {
        when(userRepository.findById(1L)).thenReturn(userEntity);
        UserDto updateDto = new UserDto(1L, "username", "password", null);

        assertThrows(ValidationException.class, () -> userService.update(1L, updateDto));
        verify(userRepository, never()).save(any(UserJpaEntity.class));
    }

    @Test
    void update_ShouldNotUpdatePassword_WhenPasswordIsEmpty() {
        when(userRepository.findById(1L)).thenReturn(userEntity);
        when(userRepository.save(any(UserJpaEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDto updateDto = new UserDto(1L, "newUsername", "", Role.USER);

        UserDto result = userService.update(1L, updateDto);

        assertNotNull(result);
        assertEquals("hashedPassword", result.password());
        verify(passwordEncoderService, never()).encode(anyString());
        verify(userRepository).save(any(UserJpaEntity.class));
    }

    @Test
    void update_ShouldNotUpdatePassword_WhenPasswordIsNull() {
        when(userRepository.findById(1L)).thenReturn(userEntity);
        when(userRepository.save(any(UserJpaEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDto updateDto = new UserDto(1L, "newUsername", null, Role.USER);

        UserDto result = userService.update(1L, updateDto);

        assertNotNull(result);
        assertEquals("hashedPassword", result.password());
        verify(passwordEncoderService, never()).encode(anyString());
        verify(userRepository).save(any(UserJpaEntity.class));
    }

    @Test
    void update_ShouldNotUpdatePassword_WhenPasswordIsSameAsExisting() {
        when(userRepository.findById(1L)).thenReturn(userEntity);
        when(userRepository.save(any(UserJpaEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDto updateDto = new UserDto(1L, "newUsername", "hashedPassword", Role.USER);

        UserDto result = userService.update(1L, updateDto);

        assertNotNull(result);
        assertEquals("hashedPassword", result.password());
        verify(passwordEncoderService, never()).encode(anyString());
        verify(userRepository).save(any(UserJpaEntity.class));
    }

    @Test
    void logout_ShouldThrowValidationException_WhenUserNotFound() {
        LoginDto loginDto = new LoginDto("username", "password", null);
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> userService.logout(loginDto));
        verify(tokenUtils, never()).deleteToken(anyLong());
    }
}
