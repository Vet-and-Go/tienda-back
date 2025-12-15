package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.service.ProductService;
import com.grupo4.VetAndGo.domain.service.impl.ProductServiceImpl;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;
import com.grupo4.VetAndGo.domain.service.impl.*;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;

class ProductServiceImplTest {
  @Test
  void create() {
    ProductDto newProduct = new ProductDto(
        1L,
        "Dog Food",
        "Premium dog food",
        "Premium dog food",
        29.99,
        100);

    ProductDto result = producServiceImpl.create(newProduct);
  }
}
