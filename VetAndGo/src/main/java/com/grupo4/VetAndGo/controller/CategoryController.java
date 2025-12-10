package com.grupo4.VetAndGo.controller;

import com.grupo4.VetAndGo.controller.mapper.CategoriaMapper;
import com.grupo4.VetAndGo.controller.webmodel.request.Category.CategoryInsert;
import com.grupo4.VetAndGo.controller.webmodel.response.Categoria.CategoriaDetail;
import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.exception.ResourceNotFoundException;
import com.grupo4.VetAndGo.domain.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDetail>> getAll() {
        List<CategoriaDetail> categories = categoryService.getAll().stream()
                .map(CategoriaMapper::fromCategoriaDtoToCategoriaDetail)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDetail> getById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));

        CategoriaDetail categoriaDetail = CategoriaMapper.fromCategoriaDtoToCategoriaDetail(categoryDto);
        return ResponseEntity.ok(categoriaDetail);
    }

    @PostMapping
    public ResponseEntity<CategoriaDetail> create(@RequestBody CategoryInsert categoryInsert) {
        CategoryDto categoryDto = CategoriaMapper.fromCategoriaInsertToCategoriaDto(categoryInsert);
        CategoryDto createdCategoria = categoryService.create(categoryDto);
        CategoriaDetail categoriaDetail = CategoriaMapper.fromCategoriaDtoToCategoriaDetail(createdCategoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaDetail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDetail> update(
            @PathVariable Long id,
            @RequestBody CategoryInsert categoriaUpdate) {

        CategoryDto categoryDto = CategoriaMapper.fromCategoriaUpdateToCategoriaDto(id, categoriaUpdate);
        CategoryDto updatedCategoria = categoryService.update(id, categoryDto);
        CategoriaDetail categoriaDetail = CategoriaMapper.fromCategoriaDtoToCategoriaDetail(updatedCategoria);

        return ResponseEntity.ok(categoriaDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
