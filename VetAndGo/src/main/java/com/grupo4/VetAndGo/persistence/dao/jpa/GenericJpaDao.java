package com.grupo4.VetAndGo.persistence.dao.jpa;

import java.util.List;
import java.util.Optional;

public interface GenericJpaDao<T> {

  Optional<T> findById(Long id);

  T insert(T entity);

  T update(T entity);

  void deleteById(Long id);

  long count();

}
