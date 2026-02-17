package com.grupo4.VetAndGo.persistence.repository.impl;

import com.grupo4.VetAndGo.domain.model.Page;
import com.grupo4.VetAndGo.domain.model.Product;
import com.grupo4.VetAndGo.persistence.dao.jpa.ProductJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {

    @Mock
    private ProductJpaDao productJpaDao;

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    @Test
    void findByName_ShouldReturnMappedProduct() {
        ProductJpaEntity entity = new ProductJpaEntity(1L, "Prod", null, "Desc", BigDecimal.TEN, 10, "img");
        when(productJpaDao.findByName("Prod")).thenReturn(Optional.of(entity));

        Optional<Product> result = productRepository.findByName("Prod");

        assertTrue(result.isPresent());
        assertEquals("Prod", result.get().getName());
    }

    @Test
    void existsByCategoryId_ShouldDelegate() {
        when(productJpaDao.existsByCategoryId(1L)).thenReturn(true);
        assertTrue(productRepository.existsByCategoryId(1L));
        verify(productJpaDao).existsByCategoryId(1L);
    }

    @Test
    void getAll_ShouldReturnPage() {
        ProductJpaEntity entity = new ProductJpaEntity(1L, "Prod", null, "Desc", BigDecimal.TEN, 10, "img");
        when(productJpaDao.findAll(1, 10, null, null, null)).thenReturn(List.of(entity));
        when(productJpaDao.count()).thenReturn(1L);

        Page<Product> result = productRepository.getAll(1, 10, null, null, null);

        assertNotNull(result);
        assertEquals(1, result.data().size());
        assertEquals(1, result.totalElements());
    }

    @Test
    void save_ShouldDelegateToInsert_WhenIdIsNull() {
        Product product = new Product(null, "Prod", null, "Desc", BigDecimal.TEN, 10, BigDecimal.ZERO, BigDecimal.TEN, "img");
        // Mapper converts to entity with null ID
        ProductJpaEntity savedEntity = new ProductJpaEntity(1L, "Prod", null, "Desc", BigDecimal.TEN, 10, "img");
        
        when(productJpaDao.insert(any(ProductJpaEntity.class))).thenReturn(savedEntity);

        Product result = productRepository.save(product);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productJpaDao).insert(any(ProductJpaEntity.class));
    }

    @Test
    void save_ShouldDelegateToUpdate_WhenIdIsNotNull() {
        Product product = new Product(1L, "Prod", null, "Desc", BigDecimal.TEN, 10, BigDecimal.ZERO, BigDecimal.TEN, "img");
        ProductJpaEntity savedEntity = new ProductJpaEntity(1L, "Prod", null, "Desc", BigDecimal.TEN, 10, "img");

        when(productJpaDao.update(any(ProductJpaEntity.class))).thenReturn(savedEntity);

        Product result = productRepository.save(product);

        assertNotNull(result);
        verify(productJpaDao).update(any(ProductJpaEntity.class));
    }

    @Test
    void deleteById_ShouldDelegate() {
        productRepository.deleteById(1L);
        verify(productJpaDao).deleteById(1L);
    }
}
