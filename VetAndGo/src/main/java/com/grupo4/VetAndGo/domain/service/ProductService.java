package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductInsert;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductUpdate;
import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.model.Page;

import java.util.Optional;

public interface ProductService {
  Page<ProductDto> getAll(int page, int size);

  ProductDto getById(Long id);

  Optional<ProductDto> findById(Long id);

  ProductDto create(ProductInsert productRequest);

  ProductDto update(ProductUpdate productRequest);

  void deleteById(Long id);
}