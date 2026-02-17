package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {"spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop"})
@Import(CategoryJpaDaoImpl.class)
class CategoryJpaDaoImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryJpaDaoImpl categoryJpaDao;

    @Test
    void findByName_ShouldReturnCategory_WhenCategoryExists() {
        CategoryJpaEntity category = new CategoryJpaEntity(null, "TestCategory", "TestDesc");
        entityManager.persist(category);
        entityManager.flush();

        Optional<CategoryJpaEntity> result = categoryJpaDao.findByName("TestCategory");

        assertTrue(result.isPresent());
        assertEquals("TestCategory", result.get().getName());
    }

    @Test
    void findByName_ShouldReturnEmpty_WhenCategoryDoesNotExist() {
        Optional<CategoryJpaEntity> result = categoryJpaDao.findByName("NonExistent");

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_ShouldReturnAllCategories() {
        CategoryJpaEntity c1 = new CategoryJpaEntity(null, "C1", "D1");
        CategoryJpaEntity c2 = new CategoryJpaEntity(null, "C2", "D2");
        entityManager.persist(c1);
        entityManager.persist(c2);
        entityManager.flush();

        List<CategoryJpaEntity> result = categoryJpaDao.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void findById_ShouldReturnCategory_WhenExists() {
        CategoryJpaEntity category = new CategoryJpaEntity(null, "ById", "Desc");
        CategoryJpaEntity persisted = entityManager.persistFlushFind(category);

        Optional<CategoryJpaEntity> result = categoryJpaDao.findById(persisted.getId());

        assertTrue(result.isPresent());
        assertEquals(persisted.getId(), result.get().getId());
    }

    @Test
    void insert_ShouldPersistCategory() {
        CategoryJpaEntity category = new CategoryJpaEntity(null, "New", "Desc");

        CategoryJpaEntity result = categoryJpaDao.insert(category);

        assertNotNull(result.getId());
        assertEquals("New", entityManager.find(CategoryJpaEntity.class, result.getId()).getName());
    }

    @Test
    void update_ShouldUpdateCategory() {
        CategoryJpaEntity category = new CategoryJpaEntity(null, "Original", "Desc");
        CategoryJpaEntity persisted = entityManager.persistFlushFind(category);

        persisted.setName("Updated");
        categoryJpaDao.update(persisted);

        assertEquals("Updated", entityManager.find(CategoryJpaEntity.class, persisted.getId()).getName());
    }

    @Test
    void deleteById_ShouldRemoveCategory() {
        CategoryJpaEntity category = new CategoryJpaEntity(null, "ToDelete", "Desc");
        CategoryJpaEntity persisted = entityManager.persistFlushFind(category);

        categoryJpaDao.deleteById(persisted.getId());

        assertNull(entityManager.find(CategoryJpaEntity.class, persisted.getId()));
    }
}
