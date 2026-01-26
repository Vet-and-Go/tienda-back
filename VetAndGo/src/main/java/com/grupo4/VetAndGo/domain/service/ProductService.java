package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.model.Page;

import java.util.Optional;

public interface ProductService {
  Page<ProductDto> getAll(int page, int size, Long categoryId, String sort, String search);

  ProductDto getById(Long id);

  Optional<ProductDto> findById(Long id);

  ProductDto create(ProductDto productDto);

  ProductDto update(ProductDto productDto);

  void deleteById(Long id);
}
