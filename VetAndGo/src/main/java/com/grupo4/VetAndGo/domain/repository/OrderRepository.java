package com.grupo4.VetAndGo.domain.repository;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.OrderJpaEntity;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
  List<OrderJpaEntity> getAll(Long userId);

  Optional<OrderJpaEntity> getById(Long id);

  OrderJpaEntity save(OrderJpaEntity orderJpaEntity);

  void delete(Long id);
}
