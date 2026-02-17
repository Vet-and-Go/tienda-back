package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {"spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop"})
@Import(UserJpaDaoImpl.class)
class UserJpaDaoImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserJpaDaoImpl userJpaDao;

    @Test
    void findByUsername_ShouldReturnUser_WhenUserExists() {
        UserJpaEntity user = new UserJpaEntity(null, "jdoe", "secret", Role.USER);
        entityManager.persist(user);
        entityManager.flush();

        Optional<UserJpaEntity> result = userJpaDao.findByUsername("jdoe");

        assertTrue(result.isPresent());
        assertEquals("jdoe", result.get().getUsername());
    }

    @Test
    void findByUsername_ShouldReturnEmpty_WhenUserDoesNotExist() {
        Optional<UserJpaEntity> result = userJpaDao.findByUsername("unknown");

        assertTrue(result.isEmpty());
    }

    @Test
    void save_ShouldPersistNewUser() {
        UserJpaEntity user = new UserJpaEntity(null, "newuser", "newpass", Role.ADMIN);

        UserJpaEntity saved = userJpaDao.insert(user);

        assertNotNull(saved.getId());
        assertEquals("newuser", entityManager.find(UserJpaEntity.class, saved.getId()).getUsername());
    }
}
