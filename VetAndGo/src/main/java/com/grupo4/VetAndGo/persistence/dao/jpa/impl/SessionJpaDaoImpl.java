package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.SessionJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;

public class SessionJpaDaoImpl implements SessionJpaDao {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void createSessionsTable(UserJpaEntity userJpaEntity) {


    }
}
