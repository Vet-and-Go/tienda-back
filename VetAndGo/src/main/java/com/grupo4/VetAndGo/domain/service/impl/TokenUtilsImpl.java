package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.exception.ValidationException;
import com.grupo4.VetAndGo.domain.mapper.UserMapper;
import com.grupo4.VetAndGo.domain.model.User;
import com.grupo4.VetAndGo.domain.repository.TokenUtilsRepository;
import com.grupo4.VetAndGo.domain.service.TokenUtils;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;

public class TokenUtilsImpl implements TokenUtils {

  private final TokenUtilsRepository tokenUtilsRepository;

  public TokenUtilsImpl(TokenUtilsRepository tokenUtilsRepository) {
    this.tokenUtilsRepository = tokenUtilsRepository;
  }

  @Override
  public User getUserbFromToken(String token) {
    UserJpaEntity user = tokenUtilsRepository.getUserFromToken(token);
    if (user == null) {
      throw new ValidationException("Invalid token.");
    }
    return UserMapper.fromUserJpaEntityToUser(user);
  }

  @Override
  public void deleteToken(String token) {
    User user = getUserbFromToken(token);
    tokenUtilsRepository.deleteToken(user.getId());

  }

  @Override
  public User validateToken(String token) {
    User user = getUserbFromToken(token);
    return user;
  }

}
