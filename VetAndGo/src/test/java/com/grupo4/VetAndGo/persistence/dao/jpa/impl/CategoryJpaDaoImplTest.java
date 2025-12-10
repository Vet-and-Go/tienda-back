package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.TestConfig;
import com.grupo4.VetAndGo.persistence.dao.jpa.CategoryJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CategoryJpaDaoImplTest {
    @Autowired
    private CategoryJpaDao categoryJpaDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testFindByName_Success() {
        // Arrange
        CategoryJpaEntity entity = new CategoryJpaEntity(null, "Alimentos", "Comida para mascotas");
        entityManager.persist(entity);
        entityManager.flush();

        // Act
        Optional<CategoryJpaEntity> result = categoryJpaDao.findByName("Alimentos");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Alimentos", result.get().getName());
    }

    @Test
    void testFindAll_Success() {
        // Arrange
        entityManager.persist(new CategoryJpaEntity(null, "Alimentos", "Descripción 1"));
        entityManager.persist(new CategoryJpaEntity(null, "Juguetes", "Descripción 2"));
        entityManager.flush();

        // Act
        List<CategoryJpaEntity> result = categoryJpaDao.findAll();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testFindById_Success() {
        // Arrange
        CategoryJpaEntity entity = new CategoryJpaEntity(null, "Alimentos", "Comida para mascotas");
        entityManager.persist(entity);
        entityManager.flush();

        // Act
        Optional<CategoryJpaEntity> result = categoryJpaDao.findById(entity.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(entity.getId(), result.get().getId());
    }

    @Test
    void testInsert_Success() {
        // Arrange
        CategoryJpaEntity newentity = new CategoryJpaEntity(null, "Medicamentos", "Descripción");

        categoryJpaDao.insert(newentity);
        assertThat(newentity.getId()).isNotNull();
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        CategoryJpaEntity entity = new CategoryJpaEntity(null, "Alimentos", "Descripción antigua");
        entityManager.persist(entity);
        entityManager.flush();

        entity.setName("Alimentos Premium");

        // Act
        CategoryJpaEntity result = categoryJpaDao.update(entity);
        entityManager.flush();

        // Assert
        assertEquals("Alimentos Premium", result.getName());
    }

    @Test
    void testDeleteById_Success() {
        // Arrange
        CategoryJpaEntity entity = new CategoryJpaEntity(null, "Alimentos", "Descripción");
        entityManager.persist(entity);
        entityManager.flush();
        Long savedId = entity.getId();

        // Act
        categoryJpaDao.deleteById(savedId);
        entityManager.flush();

        // Assert
        Optional<CategoryJpaEntity> result = categoryJpaDao.findById(savedId);
        assertFalse(result.isPresent());
    }

    @Test
    void testCount_Success() {
        // Arrange
        entityManager.persist(new CategoryJpaEntity(null, "Alimentos", "Descripción 1"));
        entityManager.persist(new CategoryJpaEntity(null, "Juguetes", "Descripción 2"));
        entityManager.flush();

        // Act
        long count = categoryJpaDao.count();

        // Assert
        assertEquals(2, count);
    }


}