package com.grupo4.VetAndGo.controller;

import java.util.List;

import com.grupo4.VetAndGo.spring.annotation.RequireAdmin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo4.VetAndGo.controller.webmodel.response.ProductResponse;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductInsert;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductUpdate;
import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.model.Page;
import com.grupo4.VetAndGo.domain.service.ProductService;
import com.grupo4.VetAndGo.controller.mapper.ProductMapper;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<Page<ProductResponse>> getAllProducts(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "10") int size) {

    Page<ProductDto> productDtoPage = productService.getAll(page, size);

    List<ProductResponse> productResponses = productDtoPage.data().stream()
        .map(ProductMapper::fromProductDtoToProductResponse)
        .toList();

    Page<ProductResponse> productResponsePage = new Page<>(
        productResponses,
        productDtoPage.pageNumber(),
        productDtoPage.pageSize(),
        productDtoPage.totalElements());
    return new ResponseEntity<>(productResponsePage, org.springframework.http.HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
    ProductDto productDto = productService.getById(id);
    ProductResponse productResponse = ProductMapper.fromProductDtoToProductResponse(productDto);
    return new ResponseEntity<>(productResponse, org.springframework.http.HttpStatus.OK);
  }

  @RequireAdmin
  @PostMapping
  public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductInsert productInsert) {
    ProductDto createdProduct = productService.create(productInsert);
    ProductResponse productResponse = ProductMapper.fromProductDtoToProductResponse(createdProduct);
    return new ResponseEntity<>(productResponse, org.springframework.http.HttpStatus.CREATED);
  }

  @RequireAdmin
  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
      @RequestBody ProductUpdate productRequest) {
    ProductDto updatedProduct = productService.update(productRequest);
    ProductResponse productResponse = ProductMapper.fromProductDtoToProductResponse(updatedProduct);
    return new ResponseEntity<>(productResponse, org.springframework.http.HttpStatus.OK);
  }

  @RequireAdmin
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteById(id);
    return new ResponseEntity<>(org.springframework.http.HttpStatus.NO_CONTENT);
  }
}
