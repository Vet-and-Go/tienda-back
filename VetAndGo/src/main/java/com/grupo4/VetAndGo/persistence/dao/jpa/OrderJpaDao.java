package com.grupo4.VetAndGo.persistence.dao.jpa;

import java.util.List;

import com.grupo4.VetAndGo.persistence.dao.jpa.entity.OrderJpaEntity;

public interface OrderJpaDao extends GenericJpaDao<OrderJpaEntity> {

  List<OrderJpaEntity> findAll();

  void deleteById(Long id);
}
