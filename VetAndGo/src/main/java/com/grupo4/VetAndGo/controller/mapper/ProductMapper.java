package com.grupo4.VetAndGo.controller.mapper;

import com.grupo4.VetAndGo.controller.webmodel.response.ProductResponse;
import com.grupo4.VetAndGo.controller.webmodel.request.ProductRequest;
import com.grupo4.VetAndGo.domain.dto.ProductDto;

public class ProductMapper {

  public static ProductResponse fromProductDtoToProductResponse(ProductDto productDto) {
    if (productDto == null) {
      return null;
    }
    return new ProductResponse(
        productDto.id(),
        productDto.name(),
        productDto.category(),
        productDto.description(),
        productDto.price(),
        productDto.stock());
  }

  public static ProductDto fromProductRequestToProductDto(ProductRequest productRequest) {
    if (productRequest == null) {
      return null;
    }
    return new ProductDto(
        productRequest.id(),
        productRequest.name(),
        productRequest.category(),
        productRequest.description(),
        productRequest.price(),
        productRequest.stock());
  }
}
