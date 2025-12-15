package com.grupo4.VetAndGo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo4.VetAndGo.controller.webmodel.request.Category.CategoryInsert;
import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @MockitoBean
    private com.grupo4.VetAndGo.domain.service.TokenUtils tokenUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll_Success() throws Exception {
        // Arrange
        CategoryDto dto1 = new CategoryDto(1L, "Alimentos", "Comida para mascotas");
        CategoryDto dto2 = new CategoryDto(2L, "Juguetes", "Juguetes para mascotas");
        List<CategoryDto> categorias = Arrays.asList(dto1, dto2);

        when(categoryService.getAll()).thenReturn(categorias);

        // Act & Assert
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Alimentos"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Juguetes"));

        verify(categoryService, times(1)).getAll();
    }

    @Test
    void testGetById_Success() throws Exception {
        // Arrange
        Long id = 1L;
        CategoryDto dto = new CategoryDto(id, "Alimentos", "Comida para mascotas");

        when(categoryService.getById(id)).thenReturn(Optional.of(dto));

        // Act & Assert
        mockMvc.perform(get("/api/categories/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alimentos"))
                .andExpect(jsonPath("$.description").value("Comida para mascotas"));

        verify(categoryService, times(1)).getById(id);
    }

    @Test
    void testGetById_NotFound() throws Exception {
        // Arrange
        Long id = 999L;
        when(categoryService.getById(id)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/categories/{id}", id))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).getById(id);
    }

    @Test
    void testCreate_Success() throws Exception {
        // Arrange
        CategoryInsert insert = new CategoryInsert("Alimentos", "Comida para mascotas");
        CategoryDto created = new CategoryDto(1L, "Alimentos", "Comida para mascotas");

        when(categoryService.create(any(CategoryDto.class))).thenReturn(created);

        // Act & Assert
        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(insert)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alimentos"))
                .andExpect(jsonPath("$.description").value("Comida para mascotas"));

        verify(categoryService, times(1)).create(any(CategoryDto.class));
    }

    @Test
    void testUpdate_Success() throws Exception {
        // Arrange
        Long id = 1L;
        CategoryInsert update = new CategoryInsert("Alimentos Premium", "Comida premium");
        CategoryDto updated = new CategoryDto(id, "Alimentos Premium", "Comida premium");

        when(categoryService.update(eq(id), any(CategoryDto.class))).thenReturn(updated);

        // Act & Assert
        mockMvc.perform(put("/api/categories/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alimentos Premium"))
                .andExpect(jsonPath("$.description").value("Comida premium"));

        verify(categoryService, times(1)).update(eq(id), any(CategoryDto.class));
    }

    @Test
    void testDelete_Success() throws Exception {
        // Arrange
        Long id = 1L;
        doNothing().when(categoryService).delete(id);

        // Act & Assert
        mockMvc.perform(delete("/api/categories/{id}", id))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).delete(id);
    }
}
