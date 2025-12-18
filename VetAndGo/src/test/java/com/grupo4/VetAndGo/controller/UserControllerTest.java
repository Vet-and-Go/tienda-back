package com.grupo4.VetAndGo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo4.VetAndGo.controller.webmodel.request.User.UserInsert;
import com.grupo4.VetAndGo.domain.dto.LoginDto;
import com.grupo4.VetAndGo.domain.dto.UserDto;
import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.domain.service.UserService;
import com.grupo4.VetAndGo.domain.service.TokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private TokenUtils tokenUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllUsers_Success() throws Exception {
        // Arrange
        UserDto user1 = new UserDto(1L, "user1", "pass1", Role.ADMIN);
        UserDto user2 = new UserDto(2L, "user2", "pass2", Role.USER);
        List<UserDto> users = Arrays.asList(user1, user2);

        when(userService.getAll()).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].username").value("user2"));

        verify(userService, times(1)).getAll();
    }

    @Test
    void testGetUserById_Success() throws Exception {
        // Arrange
        Long id = 1L;
        UserDto user = new UserDto(id, "user1", "pass1", Role.ADMIN);

        when(userService.getById(id)).thenReturn(user);

        // Act & Assert
        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"));

        verify(userService, times(1)).getById(id);
    }

    @Test
    void testGetUserByUsername_Success() throws Exception {
        // Arrange
        String username = "user1";
        UserDto user = new UserDto(1L, username, "pass1", Role.ADMIN);

        when(userService.findByUsername(username)).thenReturn(user);

        // Act & Assert
        mockMvc.perform(get("/api/users/username/{username}", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username));

        verify(userService, times(1)).findByUsername(username);
    }

    @Test
    void testCreateUser_Public_Success() throws Exception {
        // Arrange
        UserInsert insert = new UserInsert("newUser", "newPass", null);
        UserDto created = new UserDto(1L, "newUser", "newPass", Role.USER);

        when(userService.create(any(UserDto.class))).thenReturn(created);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(insert)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newUser"))
                .andExpect(jsonPath("$.role").value("USER"));

        verify(userService, times(1)).create(any(UserDto.class));
    }

    @Test
    void testUpdateUser_Success() throws Exception {
        // Arrange
        Long id = 1L;
        UserDto updateDto = new UserDto(id, "updatedUser", "newPass", Role.USER);

        when(userService.update(eq(id), any(UserDto.class))).thenReturn(updateDto);

        // Act & Assert
        mockMvc.perform(put("/api/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updatedUser"));

        verify(userService, times(1)).update(eq(id), any(UserDto.class));
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        // Arrange
        Long id = 1L;
        doNothing().when(userService).delete(id);

        // Act & Assert
        mockMvc.perform(delete("/api/users/{id}", id))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).delete(id);
    }

    @Test
    void testLogin_Success() throws Exception {
        // Arrange
        LoginDto loginDto = new LoginDto("user1", "pass1", null);
        String token = "validToken";
        UserDto userDto = new UserDto(1L, "user1", "pass1", Role.USER);

        when(userService.login(any(LoginDto.class))).thenReturn(token);
        when(userService.findByUsername("user1")).thenReturn(userDto);

        // Act & Assert
        mockMvc.perform(post("/api/users/auth/login/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.role").value("USER"));

        verify(userService, times(1)).login(any(LoginDto.class));
        verify(userService, times(1)).findByUsername("user1");
    }

    @Test
    void testLogout_Success() throws Exception {
        // Arrange
        LoginDto loginDto = new LoginDto("user1", null, null);
        doNothing().when(userService).logout(any(LoginDto.class));

        // Act & Assert
        mockMvc.perform(post("/api/users/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).logout(any(LoginDto.class));
    }
}
