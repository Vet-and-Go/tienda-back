package com.grupo4.VetAndGo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo4.VetAndGo.controller.mapper.ProductMapper;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductInsert;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductUpdate;
import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.model.Page;
import com.grupo4.VetAndGo.domain.service.ProductService;
import com.grupo4.VetAndGo.domain.service.TokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ProductService productService;

  @MockitoBean
  private TokenUtils tokenUtils;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void testGetAllProducts_Success() throws Exception {
    // Arrange
    CategoryDto categoryDto = new CategoryDto(1L, "Alimentos", "Comida para mascotas");
    ProductDto dto1 = new ProductDto(1L, "Dog Food", categoryDto, "Premium dog food", 29.99, 100);
    ProductDto dto2 = new ProductDto(2L, "Cat Food", categoryDto, "Premium cat food", 24.99, 150);
    List<ProductDto> products = Arrays.asList(dto1, dto2);
    Page<ProductDto> page = new Page<>(products, 1, 10, 2);

    when(productService.getAll(1, 10)).thenReturn(page);

    // Act & Assert
    mockMvc.perform(get("/api/products")
            .param("page", "1")
            .param("size", "10"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data[0].id").value(1))
        .andExpect(jsonPath("$.data[0].name").value("Dog Food"))
        .andExpect(jsonPath("$.data[1].id").value(2))
        .andExpect(jsonPath("$.data[1].name").value("Cat Food"))
        .andExpect(jsonPath("$.pageNumber").value(1))
        .andExpect(jsonPath("$.pageSize").value(10))
        .andExpect(jsonPath("$.totalElements").value(2));

    verify(productService, times(1)).getAll(1, 10);
  }

  @Test
  void testGetAllProducts_WithDefaultPagination() throws Exception {
    // Arrange
    CategoryDto categoryDto = new CategoryDto(1L, "Alimentos", "Comida para mascotas");
    ProductDto dto = new ProductDto(1L, "Dog Food", categoryDto, "Premium dog food", 29.99, 100);
    List<ProductDto> products = Arrays.asList(dto);
    Page<ProductDto> page = new Page<>(products, 1, 10, 1);

    when(productService.getAll(1, 10)).thenReturn(page);

    // Act & Assert
    mockMvc.perform(get("/api/products"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data[0].name").value("Dog Food"));

    verify(productService, times(1)).getAll(1, 10);
  }

  @Test
  void testGetProductById_Success() throws Exception {
    // Arrange
    Long id = 1L;
    CategoryDto categoryDto = new CategoryDto(1L, "Alimentos", "Comida para mascotas");
    ProductDto dto = new ProductDto(id, "Dog Food", categoryDto, "Premium dog food", 29.99, 100);

    when(productService.getById(id)).thenReturn(dto);

    // Act & Assert
    mockMvc.perform(get("/api/products/{id}", id))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Dog Food"))
        .andExpect(jsonPath("$.description").value("Premium dog food"))
        .andExpect(jsonPath("$.basePrice").value(29.99))
        .andExpect(jsonPath("$.finalPrice").value(29.99))
        .andExpect(jsonPath("$.stock").value(100));

    verify(productService, times(1)).getById(id);
  }

  @Test
  void testGetProductById_NotFound() throws Exception {
    // Arrange
    Long id = 999L;
    when(productService.getById(id)).thenThrow(new RuntimeException("Product not found"));

    // Act & Assert
    mockMvc.perform(get("/api/products/{id}", id))
        .andExpect(status().is5xxServerError());

    verify(productService, times(1)).getById(id);
  }

  @Test
  void testCreateProduct_Success() throws Exception {
    // Arrange
    ProductInsert insert = new ProductInsert("Dog Food", 1L, "Premium dog food", 29.99, 100);
    CategoryDto categoryDto = new CategoryDto(1L, "Alimentos", "Comida para mascotas");
    ProductDto created = new ProductDto(1L, "Dog Food", categoryDto, "Premium dog food", 29.99, 100);

    when(productService.create(any(ProductInsert.class))).thenReturn(created);

    // Act & Assert
    mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(insert)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Dog Food"))
        .andExpect(jsonPath("$.description").value("Premium dog food"))
        .andExpect(jsonPath("$.basePrice").value(29.99))
        .andExpect(jsonPath("$.finalPrice").value(29.99))
        .andExpect(jsonPath("$.stock").value(100));

    verify(productService, times(1)).create(any(ProductInsert.class));
  }

  @Test
  void testCreateProduct_InvalidData() throws Exception {
    // Arrange
    ProductInsert insert = new ProductInsert("", 1L, "", 0.0, 0);

    when(productService.create(any(ProductInsert.class)))
        .thenThrow(new IllegalArgumentException("Invalid product data"));

    // Act & Assert
    mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(insert)))
        .andExpect(status().is5xxServerError());

    verify(productService, times(1)).create(any(ProductInsert.class));
  }

  @Test
  void testUpdateProduct_Success() throws Exception {
    // Arrange
    Long id = 1L;
    ProductUpdate update = new ProductUpdate(id, "Dog Food Premium", 1L, "Premium dog food", 39.99, 150);
    CategoryDto categoryDto = new CategoryDto(1L, "Alimentos", "Comida para mascotas");
    ProductDto updated = new ProductDto(id, "Dog Food Premium", categoryDto, "Premium dog food", 39.99, 150);

    when(productService.update(any(ProductUpdate.class))).thenReturn(updated);

    // Act & Assert
    mockMvc.perform(put("/api/products/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(update)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Dog Food Premium"))
        .andExpect(jsonPath("$.basePrice").value(39.99))
        .andExpect(jsonPath("$.finalPrice").value(39.99))
        .andExpect(jsonPath("$.stock").value(150));

    verify(productService, times(1)).update(any(ProductUpdate.class));
  }

  @Test
  void testUpdateProduct_NotFound() throws Exception {
    // Arrange
    Long id = 999L;
    ProductUpdate update = new ProductUpdate(id, "Dog Food", 1L, "Premium dog food", 29.99, 100);

    when(productService.update(any(ProductUpdate.class)))
        .thenThrow(new RuntimeException("Product not found"));

    // Act & Assert
    mockMvc.perform(put("/api/products/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(update)))
        .andExpect(status().is5xxServerError());

    verify(productService, times(1)).update(any(ProductUpdate.class));
  }

  @Test
  void testDeleteProduct_Success() throws Exception {
    // Arrange
    Long id = 1L;
    doNothing().when(productService).deleteById(id);

    // Act & Assert
    mockMvc.perform(delete("/api/products/{id}", id))
        .andExpect(status().isNoContent());

    verify(productService, times(1)).deleteById(id);
  }

  @Test
  void testDeleteProduct_NotFound() throws Exception {
    // Arrange
    Long id = 999L;
    doThrow(new RuntimeException("Product not found")).when(productService).deleteById(id);

    // Act & Assert
    mockMvc.perform(delete("/api/products/{id}", id))
        .andExpect(status().is5xxServerError());

    verify(productService, times(1)).deleteById(id);
  }
}
