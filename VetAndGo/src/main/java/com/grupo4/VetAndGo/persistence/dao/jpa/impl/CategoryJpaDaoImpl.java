package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.CategoryJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class CategoryJpaDaoImpl implements CategoryJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CategoryJpaEntity> findByName(String nombre) {
        TypedQuery<CategoryJpaEntity> query = entityManager.createQuery(
                "SELECT c FROM CategoryJpaEntity c WHERE c.name = :name", CategoryJpaEntity.class);
        query.setParameter("name", nombre);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CategoryJpaEntity> findAll() {
        TypedQuery<CategoryJpaEntity> query = entityManager.createQuery(
                "SELECT c FROM CategoryJpaEntity c", CategoryJpaEntity.class);
        return query.getResultList();
    }

    @Override
    public Optional<CategoryJpaEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(CategoryJpaEntity.class, id));
    }

    @Override
    public CategoryJpaEntity insert(CategoryJpaEntity jpaEntity) {
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }

    @Override
    public CategoryJpaEntity update(CategoryJpaEntity jpaEntity) {
        return entityManager.merge(jpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        CategoryJpaEntity categoria = entityManager.find(CategoryJpaEntity.class, id);
        if (categoria != null) {
            entityManager.remove(categoria);
        }
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(c) FROM CategoryJpaEntity c", Long.class);
        return query.getSingleResult();
    }
}
