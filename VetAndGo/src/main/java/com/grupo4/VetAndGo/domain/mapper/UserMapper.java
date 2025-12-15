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
                userJpaEntity.getUsername(),
                userJpaEntity.getPassword(),
                userJpaEntity.getRole()
        );
    }
    public static UserJpaEntity FromUsertoUserJpaEntity(User user){
        if (user == null){
            return null;
        }
        return new UserJpaEntity(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
    public static User FromUserDtoToUser(UserDto userDto){
        if (userDto == null){
            return null;
        }
        return new User(
                userDto.id(),
                userDto.username(),
                userDto.password(),
                userDto.role()
        );
    }
    public static UserDto FromUserToUserDto(User user){
        if (user == null){
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}
