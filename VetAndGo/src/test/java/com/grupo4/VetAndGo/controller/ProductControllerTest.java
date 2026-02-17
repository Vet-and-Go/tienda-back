package com.grupo4.VetAndGo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductInsert;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductUpdate;
import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.model.Page;
import com.grupo4.VetAndGo.domain.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import com.grupo4.VetAndGo.spring.AuthFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AuthFilter.class))
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        CategoryDto categoryDto = new CategoryDto(1L, "Category1", "Description1");
        productDto = new ProductDto(
                1L,
                "Product1",
                categoryDto,
                "Description1",
                new BigDecimal("10.00"),
                100,
                new BigDecimal("0.0"),
                new BigDecimal("10.00"),
                "image.jpg"
        );
    }

    @Test
    @WithMockUser
    void getAllProducts_ShouldReturnProductList() throws Exception {
        Page<ProductDto> page = new Page<>(List.of(productDto), 1, 10, 1L);
        when(productService.getAll(any(Integer.class), any(Integer.class), any(), any(), any())).thenReturn(page);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].name").value("Product1"));
    }

    @Test
    @WithMockUser
    void getProductById_ShouldReturnProduct() throws Exception {
        when(productService.getById(1L)).thenReturn(productDto);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Product1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        ProductInsert productInsert = new ProductInsert(
                "Product1",
                1L,
                "Description1",
                new BigDecimal("10.00"),
                100,
                new BigDecimal("0.0"),
                "image.jpg"
        );

        when(productService.create(any(ProductDto.class))).thenReturn(productDto);

        mockMvc.perform(post("/api/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productInsert)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Product1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        ProductUpdate productUpdate = new ProductUpdate(
                1L,
                "Product1",
                1L,
                "Description1",
                new BigDecimal("10.00"),
                100,
                new BigDecimal("0.0"),
                "image.jpg"
        );

        when(productService.update(any(ProductDto.class))).thenReturn(productDto);

        mockMvc.perform(put("/api/products/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Product1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(productService).deleteById(1L);

        mockMvc.perform(delete("/api/products/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
