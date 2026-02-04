package com.grupo4.VetAndGo.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import com.grupo4.VetAndGo.persistence.dao.jpa.OrderJpaDao;
import com.grupo4.VetAndGo.domain.repository.OrderRepository;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.OrderJpaEntity;

public class OrderRepositoryImpl implements OrderRepository {

  private final OrderJpaDao orderJpaDao;

  public OrderRepositoryImpl(OrderJpaDao orderJpaDao) {
    this.orderJpaDao = orderJpaDao;
  }

  @Override
  public List<OrderJpaEntity> getAll(Long userId) {
    List<OrderJpaEntity> orders = orderJpaDao.findAll();
    return orders;
  }

  @Override
  public Optional<OrderJpaEntity> getById(Long id) {
    return orderJpaDao.findById(id);
  }

  @Override
  public OrderJpaEntity save(OrderJpaEntity orderJpaEntity) {
    if (orderJpaEntity.getId() == null) {
      return orderJpaDao.insert(orderJpaEntity);
    } else {
      return orderJpaDao.update(orderJpaEntity);
    }
  }

  @Override
  public void delete(Long id) {
    orderJpaDao.deleteById(id);
  }

}
