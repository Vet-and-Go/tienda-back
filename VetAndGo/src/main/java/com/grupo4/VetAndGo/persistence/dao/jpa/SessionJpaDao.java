package com.grupo4.VetAndGo.persistence.dao.jpa;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;

public interface SessionJpaDao {
    void createSessionsTable(UserJpaEntity userJpaEntity);
}
