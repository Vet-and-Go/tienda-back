package com.grupo4.VetAndGo.controller.mapper;

import com.grupo4.VetAndGo.controller.webmodel.request.User.UserInsert;
import com.grupo4.VetAndGo.controller.webmodel.response.User.UserDetail;
import com.grupo4.VetAndGo.controller.webmodel.response.User.UserOverview;
import com.grupo4.VetAndGo.domain.dto.UserDto;

public class UserMapper {

    public UserDetail fromUserDtoToClientDetail(UserDto userDto){
        if (userDto == null){
            return null;
        }
        return new UserDetail(
                userDto.id(),
                userDto.name(),
                userDto.username(),
                userDto.email(),
                userDto.phone(),
                userDto.address(),
                userDto.birthDate(),
                userDto.country(),
                userDto.role()
        );
    }
    public UserOverview fromUserDtoToClientOverview(UserDto userDto){
        if (userDto == null){
            return null;
        }
        return new UserOverview(
                userDto.id(),
                userDto.name(),
                userDto.username(),
                userDto.email()
        );
    }
    public UserDto fromUserInsetToClientDto(UserInsert userInsert){
        if (userInsert == null){
            return null;
        }
        return new UserDto(
                null,
                userInsert.name(),
                userInsert.username(),
                userInsert.email(),
                userInsert.password(),
                userInsert.phone(),
                userInsert.address(),
                userInsert.birthDate(),
                userInsert.country(),
                userInsert.role()
        );
    }
}
