package com.grupo4.VetAndGo.domain.mapper;

import com.grupo4.VetAndGo.domain.model.User;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import com.grupo4.VetAndGo.domain.dto.UserDto;

public class UserMapper {

  private static UserMapper INSTANCE;

  private UserMapper() {
  }

  public static UserMapper getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new UserMapper();
    }
    return INSTANCE;
  }

  public static User fromUserJpaEntityToUser(UserJpaEntity userJpaEntity) {
    if (userJpaEntity == null) {
      return null;
    }
    return new User(
        userJpaEntity.getId(),
        userJpaEntity.getUsername(),
        userJpaEntity.getPassword(),
        userJpaEntity.getRole());
  }

  public static UserJpaEntity fromUserToUserJpaEntity(User user) {
    if (user == null) {
      return null;
    }
    return new UserJpaEntity(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getRole());
  }

  public static User fromUserDtoToUser(UserDto userDto) {
    if (userDto == null) {
      return null;
    }
    return new User(
        userDto.id(),
        userDto.username(),
        userDto.password(),
        userDto.role());
  }

  public static UserDto fromUserToUserDto(User user) {
    if (user == null) {
      return null;
    }
    return new UserDto(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getRole());
  }
}
