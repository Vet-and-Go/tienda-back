package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {"spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop"})
@Import(ProductJpaDaoImpl.class)
class ProductJpaDaoImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductJpaDaoImpl productJpaDao;

    private CategoryJpaEntity category;

    @BeforeEach
    void setUp() {
        category = new CategoryJpaEntity(null, "TestCat", "Desc");
        entityManager.persist(category);
    }

    @Test
    void findAll_WithFilters_ShouldReturnFilteredProducts() {
        ProductJpaEntity p1 = new ProductJpaEntity(null, "Apple", category, "Desc", BigDecimal.valueOf(10), 10, BigDecimal.ZERO, BigDecimal.valueOf(10), "img");
        ProductJpaEntity p2 = new ProductJpaEntity(null, "Banana", category, "Desc", BigDecimal.valueOf(20), 10, BigDecimal.ZERO, BigDecimal.valueOf(20), "img");
        
        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.flush();

        // Test search
        List<ProductJpaEntity> searchResult = productJpaDao.findAll(1, 10, null, null, "app");
        assertEquals(1, searchResult.size());
        assertEquals("Apple", searchResult.get(0).getName());

        // Test category filter
        List<ProductJpaEntity> catResult = productJpaDao.findAll(1, 10, category.getId(), null, null);
        assertEquals(2, catResult.size());
    }

    @Test
    void findAll_WithSorting_ShouldReturnSortedProducts() {
        ProductJpaEntity p1 = new ProductJpaEntity(null, "B_Cheap", category, "Desc", BigDecimal.valueOf(10), 10, BigDecimal.ZERO, BigDecimal.valueOf(10), "img");
        ProductJpaEntity p2 = new ProductJpaEntity(null, "A_Expensive", category, "Desc", BigDecimal.valueOf(100), 10, BigDecimal.ZERO, BigDecimal.valueOf(100), "img");
        
        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.flush();

        // Sort by price asc
        List<ProductJpaEntity> ascResult = productJpaDao.findAll(1, 10, null, "price,asc", null);
        assertEquals("B_Cheap", ascResult.get(0).getName());

        // Sort by price desc
        List<ProductJpaEntity> descResult = productJpaDao.findAll(1, 10, null, "price,desc", null);
        assertEquals("A_Expensive", descResult.get(0).getName());
    }

    @Test
    void findByName_ShouldReturnProduct() {
        ProductJpaEntity p1 = new ProductJpaEntity(null, "UniqueName", category, "Desc", BigDecimal.TEN, 10, "img");
        entityManager.persist(p1);
        
        Optional<ProductJpaEntity> result = productJpaDao.findByName("UniqueName");
        
        assertTrue(result.isPresent());
        assertEquals("UniqueName", result.get().getName());
    }

    @Test
    void existsByCategoryId_ShouldReturnTrue_WhenProductExists() {
        ProductJpaEntity p1 = new ProductJpaEntity(null, "CatProd", category, "Desc", BigDecimal.TEN, 10, "img");
        entityManager.persist(p1);
        
        boolean exists = productJpaDao.existsByCategoryId(category.getId());
        
        assertTrue(exists);
    }
}
