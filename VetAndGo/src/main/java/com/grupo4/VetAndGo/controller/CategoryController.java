package com.grupo4.VetAndGo.controller;

import com.grupo4.VetAndGo.controller.webmodel.request.Category.CategoryInsert;
import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.service.CategoryService;
import com.grupo4.VetAndGo.spring.annotation.RequireAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getById(id);
        return ResponseEntity.ok(categoryDto);
    }

    @RequireAdmin
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryInsert categoryInsert) {
        CategoryDto categoryDto = new CategoryDto(null, categoryInsert.name(), categoryInsert.description());
        CategoryDto created = categoryService.create(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @RequireAdmin
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        CategoryDto updated = categoryService.update(id, categoryDto);
        return ResponseEntity.ok(updated);
    }

    @RequireAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
