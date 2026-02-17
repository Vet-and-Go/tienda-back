package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import com.grupo4.VetAndGo.persistence.dao.jpa.ProductJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Transactional
public class ProductJpaDaoImpl implements ProductJpaDao {

  @Override
  public Optional<ProductJpaEntity> findByName(String name) {
    TypedQuery<ProductJpaEntity> query = entityManager.createQuery(
        "SELECT p FROM ProductJpaEntity p WHERE p.name = :name",
        ProductJpaEntity.class);
    query.setParameter("name", name);
    try {
      return Optional.of(query.getSingleResult());
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public boolean existsByCategoryId(Long categoryId) {
    Long count = entityManager.createQuery(
        "SELECT COUNT(p) FROM ProductJpaEntity p WHERE p.category.id = :categoryId", Long.class)
        .setParameter("categoryId", categoryId)
        .getSingleResult();
    return count > 0;
  }

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional
  public ProductJpaEntity insert(ProductJpaEntity productJpaEntity) {
    entityManager.persist(productJpaEntity);
    return productJpaEntity;
  }

  @Override
  public void deleteById(Long id) {
    entityManager.remove(entityManager.find(ProductJpaEntity.class, id));
  }

  @Override
  public List<ProductJpaEntity> findAll() {
    return List.of();
  }

  @Override
  public Optional<ProductJpaEntity> findById(Long id) {
    return Optional.ofNullable(entityManager.find(ProductJpaEntity.class, id));
  }

  @Override
  public long count() {
    return entityManager.createQuery("SELECT COUNT(p) FROM ProductJpaEntity p", Long.class)
        .getSingleResult();
  }

  @Override
  public List<ProductJpaEntity> findAll(int page, int size, Long categoryId, String sort, String search) {
    int pageIndex = Math.max(page - 1, 0);

    StringBuilder sql = new StringBuilder("SELECT p FROM ProductJpaEntity p WHERE 1=1");

    if (categoryId != null) {
      sql.append(" AND p.category.id = :categoryId");
    }

    if (search != null && !search.isEmpty()) {
      sql.append(" AND LOWER(p.name) LIKE LOWER(:search)");
    }

    if (sort != null && !sort.isEmpty()) {
      switch (sort) {
        case "price,asc":
          sql.append(" ORDER BY p.finalPrice ASC");
          break;
        case "price,desc":
          sql.append(" ORDER BY p.finalPrice DESC");
          break;
        default:
          sql.append(" ORDER BY p.id ASC");
          break;
      }
    } else {
      sql.append(" ORDER BY p.id ASC");
    }

    TypedQuery<ProductJpaEntity> query = entityManager.createQuery(sql.toString(), ProductJpaEntity.class);

    if (categoryId != null) {
      query.setParameter("categoryId", categoryId);
    }
    
    if (search != null && !search.isEmpty()) {
      query.setParameter("search", "%" + search + "%");
    }

    query.setFirstResult(pageIndex * size);
    query.setMaxResults(size);
    return query.getResultList();
  }

  @Override
  public ProductJpaEntity update(ProductJpaEntity entity) {
    ProductJpaEntity managed = entityManager.find(ProductJpaEntity.class, entity.getId());
    if (managed == null) {
      throw new IllegalArgumentException("Product with id " + entity.getId() + " not found");
    }
    entityManager.flush();
    return entityManager.merge(entity);
  }
}
