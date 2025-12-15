package com.grupo4.VetAndGo.persistence.repository.impl;

import com.grupo4.VetAndGo.domain.repository.TokenUtilsRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.TokenUtilsJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;

public class TokenUtilsRepositoryImpl implements TokenUtilsRepository {

    private final TokenUtilsJpaDao tokenJpaDao;

    public TokenUtilsRepositoryImpl(TokenUtilsJpaDao tokenJpaDao) {
        this.tokenJpaDao = tokenJpaDao;
    }

    @Override
    public String createSessionToken(Long id) {
        return tokenJpaDao.createSessionToken(id);
    }

    @Override
    public UserJpaEntity getUserFromToken(String token) {
        return tokenJpaDao.getUserFromToken(token);
    }

    @Override
    public void deleteToken(Long id) {
        tokenJpaDao.deletesessionToken(id);

    }
}
