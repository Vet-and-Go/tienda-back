package com.grupo4.VetAndGo.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.grupo4.VetAndGo.controller.mapper.ProductMapper;
import com.grupo4.VetAndGo.controller.webmodel.request.ProductRequest;
import com.grupo4.VetAndGo.controller.webmodel.response.ProductResponse;
import com.grupo4.VetAndGo.domain.dto.ProductDto;

class ProductMapperTest {

  @Test
  void testFromProductDtoToProductResponse() {
    ProductDto productDto = new ProductDto(1L, "Dog Food", 1L, "Premium dog food", 29.99, 100);
    ProductResponse productResponse = ProductMapper.fromProductDtoToProductResponse(productDto);
    assertAll(
        () -> assertEquals(productDto.id(), productResponse.id()),
        () -> assertEquals(productDto.name(), productResponse.name()),
        () -> assertEquals(productDto.category(), productResponse.category()),
        () -> assertEquals(productDto.description(), productResponse.description()),
        () -> assertEquals(productDto.price(), productResponse.price()),
        () -> assertEquals(productDto.stock(), productResponse.stock()));
  }

  @Test
  void testFromProductRequestToProductDto() {
    ProductRequest productRequest = new ProductRequest(2L, "Cat Toy", 2L, "Fun cat toy", 9.99, 50);
    ProductDto productDto = ProductMapper.fromProductRequestToProductDto(productRequest);
    assertAll(
        () -> assertEquals(productRequest.id(), productDto.id()),
        () -> assertEquals(productRequest.name(), productDto.name()),
        () -> assertEquals(productRequest.category(), productDto.category()),
        () -> assertEquals(productRequest.description(), productDto.description()),
        () -> assertEquals(productRequest.price(), productDto.price()),
        () -> assertEquals(productRequest.stock(), productDto.stock()));
  }
}
