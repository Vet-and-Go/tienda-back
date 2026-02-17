package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.SessionJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {"spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop"})
@Import(TokenUtilsJpaDaoImpl.class)
class TokenUtilsJpaDaoImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TokenUtilsJpaDaoImpl tokenUtilsJpaDao;

    @Test
    void createSessionToken_ShouldCreateToken() {
        UserJpaEntity user = new UserJpaEntity(null, "user", "pass", Role.USER);
        entityManager.persist(user);
        entityManager.flush();

        String token = tokenUtilsJpaDao.createSessionToken(user.getId());

        assertNotNull(token);
        
        // Verify it was persisted
        UserJpaEntity retrievedUser = tokenUtilsJpaDao.getUserFromToken(token);
        assertNotNull(retrievedUser);
        assertEquals(user.getId(), retrievedUser.getId());
    }

    @Test
    void getUserFromToken_ShouldReturnUser_WhenTokenIsValid() {
        UserJpaEntity user = new UserJpaEntity(null, "user2", "pass", Role.USER);
        UserJpaEntity persistedUser = entityManager.persistFlushFind(user);

        String token = tokenUtilsJpaDao.createSessionToken(persistedUser.getId());

        UserJpaEntity result = tokenUtilsJpaDao.getUserFromToken(token);

        assertNotNull(result);
        assertEquals(persistedUser.getId(), result.getId());
    }

    @Test
    void getUserFromToken_ShouldReturnNull_WhenTokenIsInvalid() {
        UserJpaEntity result = tokenUtilsJpaDao.getUserFromToken("invalid-token");
        assertNull(result);
    }

    @Test
    void deletesessionToken_ShouldRemoveAllUserSessions() {
        UserJpaEntity user = new UserJpaEntity(null, "user3", "pass", Role.USER);
        UserJpaEntity persistedUser = entityManager.persistFlushFind(user);

        String token1 = tokenUtilsJpaDao.createSessionToken(persistedUser.getId());
        String token2 = tokenUtilsJpaDao.createSessionToken(persistedUser.getId());

        tokenUtilsJpaDao.deletesessionToken(persistedUser.getId());

        assertNull(tokenUtilsJpaDao.getUserFromToken(token1));
        assertNull(tokenUtilsJpaDao.getUserFromToken(token2));
    }
}
