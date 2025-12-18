package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.dto.UserDto;
import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.domain.model.User;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void testFromUserJpaEntitytoUser() {
        UserJpaEntity entity = new UserJpaEntity(1L, "username", "password", Role.ADMIN);
        User user = UserMapper.FromUserJpaEntitytoUser(entity);

        assertNotNull(user);
        assertEquals(entity.getId(), user.getId());
        assertEquals(entity.getUsername(), user.getUsername());
        assertEquals(entity.getPassword(), user.getPassword());
        assertEquals(entity.getRole(), user.getRole());
    }

    @Test
    void testFromUserJpaEntitytoUser_Null() {
        assertNull(UserMapper.FromUserJpaEntitytoUser(null));
    }

    @Test
    void testFromUsertoUserJpaEntity() {
        User user = new User(1L, "username", "password", Role.ADMIN);
        UserJpaEntity entity = UserMapper.FromUsertoUserJpaEntity(user);

        assertNotNull(entity);
        assertEquals(user.getId(), entity.getId());
        assertEquals(user.getUsername(), entity.getUsername());
        assertEquals(user.getPassword(), entity.getPassword());
        assertEquals(user.getRole(), entity.getRole());
    }

    @Test
    void testFromUsertoUserJpaEntity_Null() {
        assertNull(UserMapper.FromUsertoUserJpaEntity(null));
    }

    @Test
    void testFromUserDtoToUser() {
        UserDto dto = new UserDto(1L, "username", "password", Role.ADMIN);
        User user = UserMapper.FromUserDtoToUser(dto);

        assertNotNull(user);
        assertEquals(dto.id(), user.getId());
        assertEquals(dto.username(), user.getUsername());
        assertEquals(dto.password(), user.getPassword());
        assertEquals(dto.role(), user.getRole());
    }

    @Test
    void testFromUserDtoToUser_Null() {
        assertNull(UserMapper.FromUserDtoToUser(null));
    }

    @Test
    void testFromUserToUserDto() {
        User user = new User(1L, "username", "password", Role.ADMIN);
        UserDto dto = UserMapper.FromUserToUserDto(user);

        assertNotNull(dto);
        assertEquals(user.getId(), dto.id());
        assertEquals(user.getUsername(), dto.username());
        assertEquals(user.getPassword(), dto.password());
        assertEquals(user.getRole(), dto.role());
    }

    @Test
    void testFromUserToUserDto_Null() {
        assertNull(UserMapper.FromUserToUserDto(null));
    }
}
