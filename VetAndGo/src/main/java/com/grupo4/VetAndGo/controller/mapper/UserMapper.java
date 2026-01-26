package com.grupo4.VetAndGo.controller.mapper;

import com.grupo4.VetAndGo.controller.webmodel.request.User.UserInsert;
import com.grupo4.VetAndGo.controller.webmodel.response.User.UserDetail;
import com.grupo4.VetAndGo.domain.dto.UserDto;

public class UserMapper {

    public UserDetail FromUserDtoToUserDetail(UserDto userDto){
        if (userDto == null){
            return null;
        }
        return new UserDetail(
                userDto.id(),
                userDto.username(),
                userDto.username(),
                userDto.role()
        );
    }
    public UserDto FromUserInsertToUserDto(UserInsert userInsert){
        if (userInsert == null){
            return null;
        }
        return new UserDto(
                null,
                userInsert.username(),
                userInsert.password(),
                userInsert.role()
        );
    }
}
