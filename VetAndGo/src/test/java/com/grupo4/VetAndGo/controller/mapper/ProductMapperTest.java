package com.grupo4.VetAndGo.controller.mapper;

import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductInsert;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductUpdate;
import com.grupo4.VetAndGo.controller.webmodel.response.ProductResponse;
import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.dto.ProductDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    void testFromProductDtoToProductResponse_Success() {
        CategoryDto categoryDto = new CategoryDto(1L, "Alimentos", "Comida");
        ProductDto productDto = new ProductDto(1L, "Dog Food", categoryDto, "Premium", 10.0, 100);

        ProductResponse response = ProductMapper.fromProductDtoToProductResponse(productDto);

        assertNotNull(response);
        assertEquals(productDto.id(), response.id());
        assertEquals(productDto.name(), response.name());
        assertNotNull(response.category());
        assertEquals(categoryDto.id(), response.category().id());
    }

    @Test
    void testFromProductDtoToProductResponse_Null() {
        assertNull(ProductMapper.fromProductDtoToProductResponse(null));
    }

    @Test
    void testFromProductInsertToProductDto_Success() {
        ProductInsert insert = new ProductInsert("Dog Food", 1L, "Premium", 10.0, 100);
        ProductDto dto = ProductMapper.fromProductInsertToProductDto(insert);

        assertNotNull(dto);
        assertNull(dto.id());
        assertEquals(insert.name(), dto.name());
        assertNull(dto.category()); // Handled in service
    }

    @Test
    void testFromProductInsertToProductDto_Null() {
        assertNull(ProductMapper.fromProductInsertToProductDto(null));
    }

    @Test
    void testFromProductUpdateToProductDto_Success() {
        ProductUpdate update = new ProductUpdate(1L, "Dog Food", 1L, "Premium", 10.0, 100);
        ProductDto dto = ProductMapper.fromProductUpdateToProductDto(update);

        assertNotNull(dto);
        assertEquals(update.id(), dto.id());
        assertEquals(update.name(), dto.name());
        assertNull(dto.category()); // Handled in service
    }

    @Test
    void testFromProductUpdateToProductDto_Null() {
        assertNull(ProductMapper.fromProductUpdateToProductDto(null));
    }
}
