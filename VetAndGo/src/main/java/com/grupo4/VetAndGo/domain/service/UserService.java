package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.domain.dto.LoginDto;
import com.grupo4.VetAndGo.domain.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto>getAll();
    UserDto getById(Integer id);
    Optional<UserDto>findByUsername(String username);
    UserDto create(UserDto userDto);
    UserDto update(Integer id, UserDto userDto);
    void delete(Integer id);
    Optional<String> login(LoginDto loginDto);

}
