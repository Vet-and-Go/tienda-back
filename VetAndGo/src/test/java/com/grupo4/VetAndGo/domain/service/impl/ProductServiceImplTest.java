package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.service.ProductService;
import com.grupo4.VetAndGo.domain.service.impl.ProductServiceImpl;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;
import com.grupo4.VetAndGo.domain.service.impl.*;

import jakarta.persistence.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock
  ProductRepository productRepository;

  @InjectMocks
  ProductServiceImpl productServiceImpl;

  @Test
  void create() {
    ProductDto newProduct = new ProductDto(
        1L,
        "Dog Food",
        1L,
        "Premium dog food",
        29.99,
        100);

    Mockito.when(productRepository.findByName(newProduct.name())).thenReturn(java.util.Optional.empty());

    ProductJpaEntity savedEntity = new ProductJpaEntity(
        1L,
        newProduct.name(),
        newProduct.category(),
        newProduct.description(),
        newProduct.price(),
        newProduct.stock());
    Mockito.when(productRepository.save(org.mockito.ArgumentMatchers.any())).thenReturn(savedEntity);

    ProductDto result = productServiceImpl.create(newProduct);

    assertNotNull(result);
    assertEquals(newProduct.name(), result.name());
  }
}
