package com.grupo4.VetAndGo.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.grupo4.VetAndGo.controller.mapper.ProductMapper;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductInsert;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductUpdate;
import com.grupo4.VetAndGo.controller.webmodel.response.ProductResponse;
import com.grupo4.VetAndGo.domain.dto.ProductDto;

class ProductMapperTest {

  @Test
  void testFromProductDtoToProductResponse() {
    ProductDto productDto = new ProductDto(1L, "Dog Food", null, "Premium dog food", 29.99, 100, "http://image.url");
    ProductResponse productResponse = ProductMapper.FromProductDtoToProductResponse(productDto);
    assertAll(
        () -> assertEquals(productDto.id(), productResponse.id()),
        () -> assertEquals(productDto.name(), productResponse.name()),
        () -> assertEquals(productDto.description(), productResponse.description()),
        () -> assertEquals(productDto.price(), productResponse.price()),
        () -> assertEquals(productDto.stock(), productResponse.stock()),
        () -> assertEquals(productDto.imageUrl(), productResponse.imageUrl()));
  }

  @Test
  void testFromProductInsertToProductDto() {
    ProductInsert productInsert = new ProductInsert("Cat Toy", 2L, "Fun cat toy", 9.99, 50, "http://image.url");
    ProductDto productDto = ProductMapper.FromProductInsertToProductDto(productInsert);
    assertAll(
        () -> assertEquals(productInsert.name(), productDto.name()),
        // ProductInsert has category ID (Long), ProductDto has CategoryDto. Mapping probably leaves it null or handles it in service.
        // Implementation said: null, // Will be handled in the service
         () -> assertEquals(productInsert.description(), productDto.description()),
        () -> assertEquals(productInsert.price(), productDto.price()),
        () -> assertEquals(productInsert.stock(), productDto.stock()),
        () -> assertEquals(productInsert.imageUrl(), productDto.imageUrl()));
  }

  @Test
  void testFromProductUpdateToProductDto() {
      ProductUpdate productUpdate = new ProductUpdate(1L, "Updated Name", 2L, "Updated Description", 19.99, 20, "http://image.url");
      ProductDto productDto = ProductMapper.FromProductUpdateToProductDto(productUpdate);
      assertAll(
          () -> assertEquals(productUpdate.id(), productDto.id()),
          () -> assertEquals(productUpdate.name(), productDto.name()),
          // Impl sets category to null
          () -> assertEquals(productUpdate.description(), productDto.description()),
          () -> assertEquals(productUpdate.price(), productDto.price()),
          () -> assertEquals(productUpdate.stock(), productDto.stock()),
          () -> assertEquals(productUpdate.imageUrl(), productDto.imageUrl())
      );
  }
}
