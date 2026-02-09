package com.grupo4.VetAndGo.controller.mapper;

import com.grupo4.VetAndGo.controller.webmodel.response.ProductResponse;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductInsert;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductUpdate;
import com.grupo4.VetAndGo.domain.dto.ProductDto;

public class ProductMapper {

  public static ProductResponse fromProductDtoToProductResponse(ProductDto productDto) {
    if (productDto == null) {
      return null;
    }
    return new ProductResponse(
        productDto.id(),
        productDto.name(),
        CategoryMapper.fromCategoryDtoToCategoryResponse(productDto.category()),
        productDto.description(),
        productDto.basePrice(),
        productDto.stock(),
        productDto.discountPercentage(),
        productDto.finalPrice(),
        productDto.imageUrl());
  }

  public static ProductDto fromProductResponseToProductDto(ProductResponse productResponse) {
    if (productResponse == null) {
      return null;
    }
    return new ProductDto(
        productResponse.id(),
        productResponse.name(),
        CategoryMapper.fromCategoryResponseToCategoryDto(productResponse.category()),
        productResponse.description(),
        productResponse.basePrice(),
        productResponse.stock(),
        productResponse.discountPercentage(),
        productResponse.finalPrice(),
        productResponse.imageUrl());
  }

  public static ProductDto fromProductInsertToProductDto(ProductInsert productInsert) {

    if (productInsert == null) {
      return null;
    }
    return new ProductDto(
        null, // ID will be generated
        productInsert.name(),
        null, // Will be handled in the service
        productInsert.description(),
        productInsert.basePrice(),
        productInsert.stock(),
        productInsert.discountPercentage(),
        null, // Will be calculated
        productInsert.imageUrl());
  }

  public static ProductDto fromProductUpdateToProductDto(ProductUpdate productUpdate) {
    if (productUpdate == null) {
      return null;
    }
    return new ProductDto(
        productUpdate.id(),
        productUpdate.name(),
        null, // Will be handled in the service
        productUpdate.description(),
        productUpdate.basePrice(),
        productUpdate.stock(),
        productUpdate.discountPercentage(),
        null, // Will be calculated
        productUpdate.imageUrl());
  }
}
