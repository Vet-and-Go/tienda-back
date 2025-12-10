package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.model.User;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import com.grupo4.VetAndGo.domain.dto.UserDto;

public class UserMapper {

    public static User FromUserJpaEntitytoUser(UserJpaEntity userJpaEntity){
        if (userJpaEntity == null){
            return null;
        }
        return new User(
                userJpaEntity.getId(),
                userJpaEntity.getName(),
                userJpaEntity.getUsername(),
                userJpaEntity.getEmail(),
                userJpaEntity.getPassword(),
                userJpaEntity.getPhone(),
                userJpaEntity.getAddress(),
                userJpaEntity.getBirthDate(),
                userJpaEntity.getCountry(),
                userJpaEntity.getRole()
        );
    }
    public static User FromUsertoUserJpaEntity(User user){
        if (user == null){
            return null;
        }
        return new User(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getAddress(),
                user.getBirthDate(),
                user.getCountry(),
                user.getRole()
        );
    }
    public static User FromUserDtoToUser(UserDto userDto){
        if (userDto == null){
            return null;
        }
        return new User(
                userDto.id(),
                userDto.name(),
                userDto.username(),
                userDto.email(),
                userDto.password(),
                userDto.phone(),
                userDto.address(),
                userDto.birthDate(),
                userDto.country(),
                userDto.role()
        );
    }
    public static UserDto FromUserToUserDto(User user){
        if (user == null){
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getAddress(),
                user.getBirthDate(),
                user.getCountry(),
                user.getRole()
        );
    }
}
