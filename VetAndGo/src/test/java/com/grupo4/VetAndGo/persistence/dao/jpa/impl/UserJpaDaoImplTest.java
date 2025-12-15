package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.TestConfig;
import com.grupo4.VetAndGo.persistence.dao.jpa.UserJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.UserJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserJpaDaoImplTest {

    @Autowired
    private UserJpaDao userJpaDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testFindAll() {
        // Arrange
        entityManager.persist(new UserJpaEntity(null, "user1", "pass1", null));
        entityManager.persist(new UserJpaEntity(null, "user2", "pass2", null));
        entityManager.flush();

        // Act
        List<UserJpaEntity> result = userJpaDao.findAll();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testFindById() {
        // Arrange
        UserJpaEntity entity = new UserJpaEntity(null, "user1", "pass1", null);
        entityManager.persist(entity);
        entityManager.flush();
        Long id = entity.getId();

        // Act
        Optional<UserJpaEntity> result = userJpaDao.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getUsername());
    }

    @Test
    void testFindByUsername() {
        // Arrange
        UserJpaEntity entity = new UserJpaEntity(null, "user1", "pass1", null);
        entityManager.persist(entity);
        entityManager.flush();

        // Act
        Optional<UserJpaEntity> result = userJpaDao.findByUsername("user1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(entity.getId(), result.get().getId());
    }

    @Test
    void testInsert() {
        // Arrange
        UserJpaEntity entity = new UserJpaEntity(null, "newUser", "pass", null);

        // Act
        UserJpaEntity result = userJpaDao.insert(entity);
        entityManager.flush();

        // Assert
        assertNotNull(result.getId());
        assertNotNull(entityManager.find(UserJpaEntity.class, result.getId()));
    }

    @Test
    void testUpdate() {
        // Arrange
        UserJpaEntity entity = new UserJpaEntity(null, "user1", "pass1", null);
        entityManager.persist(entity);
        entityManager.flush();

        entity.setUsername("updatedUser");

        // Act
        UserJpaEntity result = userJpaDao.update(entity);
        entityManager.flush();

        // Assert
        assertEquals("updatedUser", result.getUsername());
        assertEquals("updatedUser", entityManager.find(UserJpaEntity.class, entity.getId()).getUsername());
    }

    @Test
    void testDeleteById() {
        // Arrange
        UserJpaEntity entity = new UserJpaEntity(null, "user1", "pass1", null);
        entityManager.persist(entity);
        entityManager.flush();
        Long id = entity.getId();

        // Act
        userJpaDao.deleteById(id);
        entityManager.flush();

        // Assert
        assertNull(entityManager.find(UserJpaEntity.class, id));
    }

    @Test
    void testCount() {
        // Arrange
        entityManager.persist(new UserJpaEntity(null, "user1", "pass1", null));
        entityManager.flush();

        // Act & Assert
        assertEquals(1, userJpaDao.count());
    }
}
