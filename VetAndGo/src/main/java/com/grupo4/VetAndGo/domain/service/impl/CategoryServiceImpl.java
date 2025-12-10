package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.mapper.CategoryMapper;
import com.grupo4.VetAndGo.domain.repository.CategoryRepository;
import com.grupo4.VetAndGo.domain.service.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::FromCategoriaEntityJpatoCategoria)
                .map(CategoryMapper::FromCategoriaToCategoriaDto)
                .collect(Collectors.toList());
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
            throw new IllegalArgumentException("La categoría con nombre " + categoryDto.id() + " ya existe.");
        }

        System.out.println("Creating category: " + categoryDto);

        var categoria = CategoryMapper.FromCategoriaDtoToCategoria(categoryDto);
        var saved = categoryRepository.save(CategoryMapper.FromCategoriaToCategoriaEntityJpa(categoria));
        var result = CategoryMapper.FromCategoriaEntityJpatoCategoria(saved);
        return new CategoryDto(result.getId(), result.getName(), result.getDescription());
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
        categoryRepository.deleteById(id);

    }
}
