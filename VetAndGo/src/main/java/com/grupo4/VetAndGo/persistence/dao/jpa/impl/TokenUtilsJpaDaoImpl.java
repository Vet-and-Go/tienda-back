package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.TokenUtilsJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.SessionJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TokenUtilsJpaDaoImpl implements TokenUtilsJpaDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public String createSessionToken(Long userId) {
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        SessionJpaEntity sessionToken = new SessionJpaEntity(token, userId, now);

        entityManager.persist(sessionToken);
        entityManager.flush();

        return token;
    }

    @Override
    @Transactional
    public void deletesessionToken(Long idUser) {
        TypedQuery<SessionJpaEntity> query = entityManager.createQuery(
                "SELECT s FROM SessionJpaEntity s WHERE s.userId = :userId",
                SessionJpaEntity.class
        );
        query.setParameter("userId", idUser);
        List<SessionJpaEntity> sessions = query.getResultList();
        for (SessionJpaEntity session : sessions) {
            entityManager.remove(session);
        }
        entityManager.flush();
    }

    @Override
    public UserJpaEntity getUserFromToken(String token) {
        TypedQuery<SessionJpaEntity> sessionQuery = entityManager.createQuery(
                "SELECT s FROM SessionJpaEntity s WHERE s.token = :token",
                SessionJpaEntity.class
        );
        sessionQuery.setParameter("token", token);
        try {
            SessionJpaEntity session = sessionQuery.getSingleResult();
            return entityManager.find(UserJpaEntity.class, session.getUserId());
        } catch (Exception e) {
            return null;
        }
    }
}
