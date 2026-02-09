package com.grupo4.VetAndGo.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import com.grupo4.VetAndGo.persistence.dao.jpa.OrderJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.OrderJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Transactional
public class OrderJpaDaoImpl implements OrderJpaDao {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<OrderJpaEntity> findById(Long id) {
    return Optional.ofNullable(entityManager.find(OrderJpaEntity.class, id));
  }

  @Override
  public OrderJpaEntity insert(OrderJpaEntity jpaEntity) {
    entityManager.persist(jpaEntity);
    return jpaEntity;
  }

  @Override
  public OrderJpaEntity update(OrderJpaEntity jpaEntity) {
    OrderJpaEntity managed = entityManager.find(OrderJpaEntity.class, jpaEntity.getId());
    if (managed == null) {
      throw new IllegalArgumentException("Order with ID " + jpaEntity.getId() + " does not exist.");
    }
    return entityManager.merge(jpaEntity);
  }

  @Override
  public long count() {
    return entityManager.createQuery("SELECT COUNT(o) FROM OrderJpaEntity o", Long.class)
        .getSingleResult();
  }

  @Override
  public List<OrderJpaEntity> findAll() {
    TypedQuery<OrderJpaEntity> query = entityManager.createQuery(
        "SELECT o FROM OrderJpaEntity o", 
        OrderJpaEntity.class);
    return query.getResultList();
  }

  @Override
  public void deleteById(Long id) {
    OrderJpaEntity order = entityManager.find(OrderJpaEntity.class, id);
    if (order != null) {
      entityManager.remove(order);
    }
  }

}
