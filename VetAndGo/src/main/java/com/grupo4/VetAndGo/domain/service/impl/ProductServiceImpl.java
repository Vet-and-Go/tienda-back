package com.grupo4.VetAndGo.domain.service.impl;

import java.util.List;
import java.util.Optional;

import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.mapper.ProductMapper;
import com.grupo4.VetAndGo.domain.model.Page;
import com.grupo4.VetAndGo.domain.model.Product;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.domain.service.ProductService;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;

import jakarta.transaction.Transactional;

public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  @Transactional
  public ProductDto create(ProductDto productDto) {
    if (productRepository.findByName(productDto.name()).isPresent()) {
      throw new IllegalArgumentException("Cannot create a product with an existing ID.");
    }
    System.out.println("Creating product: " + productDto);

    var product = ProductMapper.getInstance().fromProductDtoToProduct(productDto);
    var saved = productRepository.save(ProductMapper.getInstance().fromProductToProductJpaEntity(product));
    var result = ProductMapper.getInstance().fromProductJpaEntityToProduct(saved);

    return new ProductDto(
        result.getId(),
        result.getName(),
        result.getCategory(),
        result.getDescription(),
        result.getPrice(),
        result.getStock());
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    ProductDto existingProductDto = getById(id);

    if (existingProductDto == null) {
      throw new RuntimeException("Product not found");
    }
    productRepository.deleteById(id);
  }

  @Override
  public Page<ProductDto> getAll(int page, int size) {
    if (page < 1 || size < 1) {
      throw new IllegalArgumentException("Invalid page or size");
    }
    Page<ProductJpaEntity> productPage = productRepository
        .getAll(page, size);
    List<ProductDto> productDtos = productPage.data().stream()
        .map(ProductMapper.getInstance()::fromProductJpaEntityToProduct)
        .map(ProductMapper.getInstance()::fromProductToProductDto)
        .toList();
    return new Page<>(
        productDtos,
        productPage.pageNumber(),
        productPage.pageSize(),
        productPage.totalElements());
  }

  @Override
  public ProductDto getById(Long id) {
    return productRepository
        .findById(id)
        .map(ProductMapper.getInstance()::fromProductJpaEntityToProduct)
        .map(ProductMapper.getInstance()::fromProductToProductDto)
        .orElseThrow(() -> new RuntimeException("Product not found"));
  }

  @Override
  public Optional<ProductDto> findById(Long id) {
    return productRepository.findById(id)
        .map(ProductMapper.getInstance()::fromProductJpaEntityToProduct)
        .map(ProductMapper.getInstance()::fromProductToProductDto);
  }

  @Override
  @Transactional
  public ProductDto update(ProductDto productDto) {
    if (productRepository.findByName(productDto.name()).isPresent()) {
      throw new IllegalArgumentException("Cannot create a product with an existing ID.");
    }
    getById(productDto.id());
    ProductJpaEntity newProductJpaEntity = buildProductJpaEntityFromProductDto(productDto);

    return ProductMapper.getInstance().fromProductToProductDto(
        ProductMapper.getInstance().fromProductJpaEntityToProduct(
            productRepository.save(newProductJpaEntity)));
  }

  private ProductJpaEntity buildProductJpaEntityFromProductDto(ProductDto productDto) {
    Product newProduct = ProductMapper.getInstance().fromProductDtoToProduct(productDto);
    return ProductMapper.getInstance().fromProductToProductJpaEntity(newProduct);
  }
}
