package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.domain.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDto> getAll();
    Optional<CategoryDto> getById(Long id);
    CategoryDto create(CategoryDto categoryDto);
    CategoryDto update(Long id, CategoryDto categoryDto);
    void delete(Long id);
}
