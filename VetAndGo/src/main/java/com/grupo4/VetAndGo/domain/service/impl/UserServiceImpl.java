package com.grupo4.VetAndGo.domain.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.grupo4.VetAndGo.domain.dto.LoginDto;
import com.grupo4.VetAndGo.domain.dto.UserDto;
import com.grupo4.VetAndGo.domain.mapper.UserMapper;
import com.grupo4.VetAndGo.domain.repository.UserRepository;
import com.grupo4.VetAndGo.domain.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAll() {
        return List.of();
    }

    @Override
    public UserDto getById(Integer id) {
        return null;
    }

    @Override
    public Optional<UserDto> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public UserDto create(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto update(Integer id, UserDto userDto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
    @Override
    public Optional<String> login(LoginDto loginDto) {
//        return userRepository.findByUsername(loginDto.username())
//                .filter(user -> BCrypt.verifyer().verify(
//                        loginDto.password().toCharArray(),
//                        user.getPassword()
//                ).verified)
//                .map(UserMapper::FromUserJpaEntitytoUser)
//                .map(UserMapper::FromUserToUserDto)
//                .map(jwtService::generateToken);
        return Optional.empty();
    }
}
