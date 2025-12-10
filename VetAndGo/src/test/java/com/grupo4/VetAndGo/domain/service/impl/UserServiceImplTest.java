package com.grupo4.VetAndGo.domain.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.grupo4.VetAndGo.domain.dto.LoginDto;
import com.grupo4.VetAndGo.domain.dto.UserDto;
import com.grupo4.VetAndGo.domain.model.User;
import com.grupo4.VetAndGo.domain.repository.UserRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

}
