package com.grupo4.VetAndGo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo4.VetAndGo.controller.webmodel.request.Category.CategoryInsert;
import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import com.grupo4.VetAndGo.spring.AuthFilter;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthFilter.class))
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        categoryDto = new CategoryDto(1L, "Category1", "Description1");
    }

    @Test
    @WithMockUser
    void getAllCategories_ShouldReturnList() throws Exception {
        when(categoryService.getAll()).thenReturn(List.of(categoryDto));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Category1"));
    }

    @Test
    @WithMockUser
    void getCategoryById_ShouldReturnCategory() throws Exception {
        when(categoryService.getById(1L)).thenReturn(Optional.of(categoryDto));

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Category1"));
    }

    @Test
    @WithMockUser
    void getCategoryById_ShouldReturnNotFound() throws Exception {
        when(categoryService.getById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createCategory_ShouldReturnCreatedCategory() throws Exception {
        CategoryInsert insert = new CategoryInsert("Category1", "Description1");

        when(categoryService.create(any(CategoryDto.class))).thenReturn(categoryDto);

        mockMvc.perform(post("/api/categories")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(insert)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateCategory_ShouldReturnUpdatedCategory() throws Exception {
        when(categoryService.update(anyLong(), any(CategoryDto.class))).thenReturn(categoryDto);

        mockMvc.perform(put("/api/categories/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteCategory_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/categories/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
