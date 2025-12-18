package com.grupo4.VetAndGo.domain.service;

import com.grupo4.VetAndGo.domain.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAll();

    CategoryDto getById(Long id);

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(Long id, CategoryDto categoryDto);

    void delete(Long id);
}
