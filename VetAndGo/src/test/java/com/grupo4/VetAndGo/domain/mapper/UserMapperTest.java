package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.dto.UserDto;
import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.domain.model.User;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void testFromUserJpaEntitytoUser_Success() {
        // Arrange
        UserJpaEntity entity = new UserJpaEntity(1L, "username", "password", Role.USER);

        // Act
        User result = UserMapper.FromUserJpaEntitytoUser(entity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("username", result.getUsername());
        assertEquals("password", result.getPassword());
        assertEquals(Role.USER, result.getRole());
    }

    @Test
    void testFromUserJpaEntitytoUser_Null() {
        assertNull(UserMapper.FromUserJpaEntitytoUser(null));
    }

    @Test
    void testFromUsertoUserJpaEntity_Success() {
        // Arrange
        User user = new User(1L, "username", "password", Role.USER);

        // Act
        UserJpaEntity result = UserMapper.FromUsertoUserJpaEntity(user);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("username", result.getUsername());
        assertEquals("password", result.getPassword());
        assertEquals(Role.USER, result.getRole());
    }

    @Test
    void testFromUsertoUserJpaEntity_Null() {
        assertNull(UserMapper.FromUsertoUserJpaEntity(null));
    }

    @Test
    void testFromUserDtoToUser_Success() {
        // Arrange
        UserDto dto = new UserDto(1L, "username", "password", Role.USER);

        // Act
        User result = UserMapper.FromUserDtoToUser(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("username", result.getUsername());
        assertEquals("password", result.getPassword());
        assertEquals(Role.USER, result.getRole());
    }

    @Test
    void testFromUserDtoToUser_Null() {
        assertNull(UserMapper.FromUserDtoToUser(null));
    }

    @Test
    void testFromUserToUserDto_Success() {
        // Arrange
        User user = new User(1L, "username", "password", Role.USER);

        // Act
        UserDto result = UserMapper.FromUserToUserDto(user);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("username", result.username());
        assertEquals("password", result.password());
        assertEquals(Role.USER, result.role());
    }

    @Test
    void testFromUserToUserDto_Null() {
        assertNull(UserMapper.FromUserToUserDto(null));
    }
}
