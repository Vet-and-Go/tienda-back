package com.grupo4.VetAndGo.persistence;

import com.grupo4.VetAndGo.persistence.dao.jpa.CategoryJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.impl.CategoryJpaDaoImpl;
import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestConfiguration
@Profile("test")
@EnableJpaRepositories(basePackages = "com.grupo4.VetAndGo.persistence.dao.jpa")
@EntityScan(basePackages = "com.grupo4.VetAndGo.persistence.dao.jpa.entity")
public class TestConfig {
    @Bean
    public CategoryJpaDao categoriaJpaDao(EntityManager entityManager) {
        return new CategoryJpaDaoImpl();
    }
}
