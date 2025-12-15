package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.dto.LoginDto;
import com.grupo4.VetAndGo.domain.dto.UserDto;
import com.grupo4.VetAndGo.domain.mapper.UserMapper;
import com.grupo4.VetAndGo.domain.model.User;
import com.grupo4.VetAndGo.domain.repository.TokenUtilsRepository;
import com.grupo4.VetAndGo.domain.repository.UserRepository;
import com.grupo4.VetAndGo.domain.service.PasswordEncoderService;
import com.grupo4.VetAndGo.domain.service.UserService;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final PasswordEncoderService passwordEncoderService;
    private final TokenUtilsRepository tokenUtils;
    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoderService passwordEncoderService,
                          TokenUtilsRepository tokenUtils,
                          UserRepository userRepository) {
        this.passwordEncoderService = passwordEncoderService;
        this.tokenUtils = tokenUtils;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::FromUserJpaEntitytoUser)
                .map(UserMapper::FromUserToUserDto)
                .toList();
    }

    @Override
    public UserDto getById(Long id) {
        UserJpaEntity user = userRepository.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        return UserMapper.FromUserToUserDto(UserMapper.FromUserJpaEntitytoUser(user));
    }

    @Override
    public UserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::FromUserJpaEntitytoUser)
                .map(UserMapper::FromUserToUserDto)
                .orElseThrow(() -> new IllegalArgumentException("User with username " + username + " not found"));
    }

    @Override
    public UserDto create(UserDto userDto) {
        String hashedPassword = passwordEncoderService.encode(userDto.password());
        UserDto userToSave = new UserDto(null, userDto.username(), hashedPassword, userDto.role());

        User user = UserMapper.FromUserDtoToUser(userToSave);
        UserJpaEntity savedUser = userRepository.save(UserMapper.FromUsertoUserJpaEntity(user));
        return UserMapper.FromUserToUserDto(UserMapper.FromUserJpaEntitytoUser(savedUser));
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        UserJpaEntity existingUser = userRepository.findById(id);
        if (existingUser == null) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }

        String password = determinePassword(userDto.password(), existingUser.getPassword());

        UserDto userToUpdate = new UserDto(
                id,
                userDto.username() != null ? userDto.username() : existingUser.getUsername(),
                password,
                userDto.role() != null ? userDto.role() : existingUser.getRole()
        );

        User user = UserMapper.FromUserDtoToUser(userToUpdate);
        UserJpaEntity updatedUser = userRepository.save(UserMapper.FromUsertoUserJpaEntity(user));
        return UserMapper.FromUserToUserDto(UserMapper.FromUserJpaEntitytoUser(updatedUser));
    }

    @Override
    public void delete(Long id) {
        UserJpaEntity existingUser = userRepository.findById(id);
        if (existingUser == null) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        userRepository.delete(id);
    }

    @Override
    public String login(LoginDto loginDto) {
        UserJpaEntity user = userRepository.findByUsername(loginDto.username())
                .orElseThrow(() -> new IllegalArgumentException("User " + loginDto.username() + " not found."));

        if (!passwordEncoderService.verify(loginDto.password(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password for user " + loginDto.username() + ".");
        }
        return tokenUtils.createSessionToken(user.getId());
    }

    @Override
    public void logout(LoginDto loginDto) {
        UserJpaEntity user = userRepository.findByUsername(loginDto.username())
                .orElseThrow(() -> new IllegalArgumentException("User " + loginDto.username() + " not found."));
        tokenUtils.deleteToken(user.getId());
    }

    private String determinePassword(String newPassword, String existingPassword) {
        if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals(existingPassword)) {
            return passwordEncoderService.encode(newPassword);
        }
        return existingPassword;
    }
}
