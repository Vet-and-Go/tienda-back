package com.grupo4.VetAndGo.spring;

import com.grupo4.VetAndGo.persistence.dao.jpa.CategoryJpaDao;
import com.grupo4.VetAndGo.domain.repository.CategoryRepository;
import com.grupo4.VetAndGo.domain.service.CategoryService;
import com.grupo4.VetAndGo.domain.service.impl.CategoryServiceImpl;
import com.grupo4.VetAndGo.persistence.dao.jpa.impl.CategoryJpaDaoImpl;
import com.grupo4.VetAndGo.persistence.repository.impl.CategoryRepositoryImpl;
import com.grupo4.VetAndGo.persistence.dao.jpa.ProductJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.impl.ProductJpaDaoImpl;
import com.grupo4.VetAndGo.persistence.repository.impl.ProductRepositoryImpl;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.domain.service.ProductService;
import com.grupo4.VetAndGo.domain.service.impl.ProductServiceImpl;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Profile("!test")
@EnableJpaRepositories(basePackages = "com.grupo4.VetAndGo.persistence.dao.jpa")
@EntityScan(basePackages = "com.grupo4.VetAndGo.persistence.dao.jpa.entity")

public class SpringConfig {
  @Bean
  public CategoryRepository categoryRepository(CategoryJpaDao categoryJpaDao) {
    return new CategoryRepositoryImpl(categoryJpaDao);
  }

  @Bean
  public CategoryService categoryService(CategoryRepository categoryRepository) {
    return new CategoryServiceImpl(categoryRepository);
  }

  @Bean
  public CategoryJpaDao categoryJpaDao() {

    return new CategoryJpaDaoImpl();
  }

  @Bean
  public ProductJpaDao productJpaDao() {
    return new ProductJpaDaoImpl();
  }

  @Bean
  public ProductRepository productRepository(ProductJpaDao productJpaDao) {
    return new ProductRepositoryImpl(productJpaDao);
  }

  @Bean
  public ProductService productService(ProductRepository productRepository) {
    return new ProductServiceImpl(productRepository);
  }
}
