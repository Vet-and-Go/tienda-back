package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.mapper.CategoryMapper;
import com.grupo4.VetAndGo.domain.repository.CategoryRepository;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.domain.service.CategoryService;
import com.grupo4.VetAndGo.domain.exception.BussinesException;
import com.grupo4.VetAndGo.domain.exception.ResourceNotFoundException;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;

  public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
  }

  @Override
  public List<CategoryDto> getAll() {
    List<CategoryJpaEntity> categories = categoryRepository.findAll();
    if (categories.isEmpty()) {
      throw new ResourceNotFoundException("No se encontraron categorías.");
    }
    return categories.stream()
        .map(CategoryMapper::fromCategoryJpaEntityToCategory)
        .map(CategoryMapper::fromCategoryToCategoryDto)
        .toList();
  }

  @Override
  public Optional<CategoryDto> getById(Long id) {
    Optional<CategoryJpaEntity> category = categoryRepository.findById(id);
    if (category.isEmpty()) {
      throw new ResourceNotFoundException("No se encontró la categoría con ID " + id + ".");
    }
    return category
        .map(CategoryMapper::fromCategoryJpaEntityToCategory)
        .map(CategoryMapper::fromCategoryToCategoryDto);
  }

  @Override
  public CategoryDto create(CategoryDto categoryDto) {
    if (categoryRepository.findByName(categoryDto.name()).isPresent()) {
      throw new BussinesException("La categoría con nombre '" + categoryDto.name() + "' ya existe.");
    }

    var categoria = CategoryMapper.fromCategoryDtoToCategory(categoryDto);
    var saved = categoryRepository.save(CategoryMapper.fromCategoryToCategoryJpaEntity(categoria));
    var result = CategoryMapper.fromCategoryJpaEntityToCategory(saved);
    return CategoryMapper.fromCategoryToCategoryDto(result);
  }

  @Override
  public CategoryDto update(Long id, CategoryDto categoryDto) {

    return categoryRepository.findById(id)
        .map(existing -> {
          existing.setName(categoryDto.name());
          existing.setDescription(categoryDto.description());
          return categoryRepository.save(existing);
        })
        .map(CategoryMapper::fromCategoryJpaEntityToCategory)
        .map(CategoryMapper::fromCategoryToCategoryDto)
        .orElseThrow(() -> new ResourceNotFoundException("La categoría con ID " + id + " no existe."));
  }

  @Override
  public void delete(Long id) {
    if (!categoryRepository.findById(id).isPresent()) {
      throw new ResourceNotFoundException("La categoría con ID " + id + " no existe.");

    }
    if (productRepository.existsByCategoryId(id)) {
      throw new BussinesException(
          "No se puede eliminar la categoría con ID " + id + " porque está asociada a un producto.");
    }

    categoryRepository.deleteById(id);

  }
}
