package com.grupo4.VetAndGo.controller.mapper;

import com.grupo4.VetAndGo.controller.webmodel.request.User.UserInsert;
import com.grupo4.VetAndGo.controller.webmodel.response.User.UserDetail;
import com.grupo4.VetAndGo.domain.dto.UserDto;

public class UserMapper {

  public UserDetail fromUserDtoToClientDetail(UserDto userDto) {
    if (userDto == null) {
      return null;
    }
    return new UserDetail(
        userDto.id(),
        userDto.username(),
        userDto.password(),
        userDto.role());
  }

  public static UserDto fromUserDetailToUserDto(UserDetail userDetail) {
    if (userDetail == null) {
      return null;
    }
    return new UserDto(
        userDetail.id(),
        userDetail.name(),
        userDetail.username(),
        userDetail.role());
  }

  public UserDto fromUserInsetToClientDto(UserInsert userInsert) {
    if (userInsert == null) {
      return null;
    }
    return new UserDto(
        null,
        userInsert.username(),
        userInsert.password(),
        userInsert.role());
  }

  public static UserDetail fromUserDtoToUserDetail(UserDto userDto) {
    if (userDto == null) {
      return null;
    }
    return new UserDetail(
        userDto.id(),
        userDto.username(),
        userDto.password(),
        userDto.role());
  }
}
