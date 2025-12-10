package com.grupo4.VetAndGo.persistence.repository.impl;

import com.grupo4.VetAndGo.domain.repository.CategoryRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.CategoryJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryJpaDao categoryJpaDao;

    public CategoryRepositoryImpl(CategoryJpaDao categoryJpaDao) {
        this.categoryJpaDao = categoryJpaDao;
    }

    @Override
    public List<CategoryJpaEntity> findAll() {
        return categoryJpaDao.findAll();
    }

    @Override
    public Optional<CategoryJpaEntity> findById(Long id) {
        return categoryJpaDao.findById(id);
    }

    @Override
    public CategoryJpaEntity save(CategoryJpaEntity categoria) {
        if (categoria.getId() == null) {
            return categoryJpaDao.insert(categoria);
        } else {
            return categoryJpaDao.update(categoria);
        }
    }

    @Override
    public void deleteById(Long id) {
        categoryJpaDao.deleteById(id);
    }

    @Override
    public Optional<CategoryJpaEntity> findByName(String name) {
        return categoryJpaDao.findByName(name);
    }

}
