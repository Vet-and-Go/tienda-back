package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.mapper.CategoryMapper;
import com.grupo4.VetAndGo.domain.repository.CategoryRepository;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.domain.service.CategoryService;

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
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::FromCategoriaEntityJpatoCategoria)
                .map(CategoryMapper::FromCategoriaToCategoriaDto)
                .toList();
    }

    @Override
    public Optional<CategoryDto> getById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper::FromCategoriaEntityJpatoCategoria)
                .map(CategoryMapper::FromCategoriaToCategoriaDto);
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        if (categoryRepository.findByName(categoryDto.name()).isPresent()) {
            throw new IllegalArgumentException("La categoría con nombre '" + categoryDto.name() + "' ya existe.");
        }

        var categoria = CategoryMapper.FromCategoriaDtoToCategoria(categoryDto);
        var saved = categoryRepository.save(CategoryMapper.FromCategoriaToCategoriaEntityJpa(categoria));
        var result = CategoryMapper.FromCategoriaEntityJpatoCategoria(saved);
        return CategoryMapper.FromCategoriaToCategoriaDto(result);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(categoryDto.name());
                    existing.setDescription(categoryDto.description());
                    return categoryRepository.save(existing);
                })
                .map(CategoryMapper::FromCategoriaEntityJpatoCategoria)
                .map(CategoryMapper::FromCategoriaToCategoriaDto)
                .orElseThrow(() -> new IllegalArgumentException("La categoría con ID " + id + " no existe."));
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("La categoría con ID " + id + " no existe.");

        }
        if (productRepository.existsByCategoryId(id)) {
            throw new IllegalArgumentException(
                    "No se puede eliminar la categoría con ID " + id + " porque está asociada a productos.");
        }

        categoryRepository.deleteById(id);

    }
}