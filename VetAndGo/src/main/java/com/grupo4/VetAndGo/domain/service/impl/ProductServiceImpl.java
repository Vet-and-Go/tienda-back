package com.grupo4.VetAndGo.domain.service.impl;

import java.util.List;
import java.util.Optional;

import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.mapper.ProductMapper;
import com.grupo4.VetAndGo.domain.model.Page;
import com.grupo4.VetAndGo.domain.model.Product;
import com.grupo4.VetAndGo.domain.repository.CategoryRepository;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.domain.service.ProductService;

import jakarta.transaction.Transactional;

public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  public ProductServiceImpl(ProductRepository productRepository,
      CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
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
    var saved = productRepository.save(product);

    return ProductMapper.getInstance().fromProductToProductDto(saved);
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
  public Page<ProductDto> getAll(int page, int size, Long categoryId, String sort, String search) {
    if (page < 1 || size < 1) {
      throw new IllegalArgumentException("Invalid page or size");
    }
    Page<Product> productPage = productRepository.getAll(page, size, categoryId, sort, search);

    List<ProductDto> productDtos = productPage.data().stream()
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
        .map(ProductMapper.getInstance()::fromProductToProductDto)
        .orElseThrow(() -> new RuntimeException("Product not found"));
  }

  @Override
  public Optional<ProductDto> findById(Long id) {
    return productRepository.findById(id)
        .map(ProductMapper.getInstance()::fromProductToProductDto);
  }

  @Override
  @Transactional
  public ProductDto update(ProductDto productDto) {
    if (productRepository.findByName(productDto.name()).isPresent()) {
      throw new IllegalArgumentException("Cannot create a product with an existing ID.");
    }
    getById(productDto.id());

    Product newProduct = ProductMapper.getInstance().fromProductDtoToProduct(productDto);
    return ProductMapper.getInstance().fromProductToProductDto(
        productRepository.save(newProduct));
  }

}
