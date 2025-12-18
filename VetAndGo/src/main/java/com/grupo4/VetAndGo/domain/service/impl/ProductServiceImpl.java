package com.grupo4.VetAndGo.domain.service.impl;

import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductInsert;
import com.grupo4.VetAndGo.controller.webmodel.request.product.ProductUpdate;
import com.grupo4.VetAndGo.domain.dto.CategoryDto;
import com.grupo4.VetAndGo.domain.dto.ProductDto;
import com.grupo4.VetAndGo.domain.exception.BussinesException;
import com.grupo4.VetAndGo.domain.exception.ResourceNotFoundException;
import com.grupo4.VetAndGo.domain.exception.ValidationException;
import com.grupo4.VetAndGo.domain.mapper.CategoryMapper;
import com.grupo4.VetAndGo.domain.mapper.ProductMapper;
import com.grupo4.VetAndGo.domain.model.Page;
import com.grupo4.VetAndGo.domain.model.Product;
import com.grupo4.VetAndGo.domain.repository.CategoryRepository;
import com.grupo4.VetAndGo.domain.repository.ProductRepository;
import com.grupo4.VetAndGo.domain.service.ProductService;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.ProductJpaEntity;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  @Transactional
  public ProductDto create(ProductInsert productInsert) {
    if (productRepository.findByName(productInsert.name()).isPresent()) {
      throw new BussinesException("Cannot create a product with an existing name.");
    }

    CategoryDto categoryDto = categoryRepository.findById(productInsert.category())
        .map(CategoryMapper::FromCategoriaEntityJpatoCategoria)
        .map(CategoryMapper::FromCategoriaToCategoriaDto)
        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

    ProductDto productDto = new ProductDto(
        null,
        productInsert.name(),
        categoryDto,
        productInsert.description(),
        productInsert.price(),
        productInsert.stock());

    var product = ProductMapper.getInstance().fromProductDtoToProduct(productDto);
    var saved = productRepository.save(ProductMapper.getInstance().fromProductToProductJpaEntity(product));
    var result = ProductMapper.getInstance().fromProductJpaEntityToProduct(saved);

    return ProductMapper.getInstance().fromProductToProductDto(result);
  }

  @Override
  public void deleteById(Long id) {
    getById(id);
    productRepository.deleteById(id);
  }

  @Override
  public Page<ProductDto> getAll(int page, int size) {
    if (page < 1 || size < 1) {
      throw new ValidationException("Invalid page or size");
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
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
  }

  @Override
  public Optional<ProductDto> findById(Long id) {
    return productRepository.findById(id)
        .map(ProductMapper.getInstance()::fromProductJpaEntityToProduct)
        .map(ProductMapper.getInstance()::fromProductToProductDto);
  }

  @Override
  public ProductDto update(ProductUpdate productUpdate) {
    return productRepository.findById(productUpdate.id())
        .map(existingProductJpaEntity -> {
          CategoryDto categoryDto = categoryRepository.findById(productUpdate.category())
              .map(CategoryMapper::FromCategoriaEntityJpatoCategoria)
              .map(CategoryMapper::FromCategoriaToCategoriaDto)
              .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

          ProductDto updatedProductDto = new ProductDto(
              productUpdate.id(),
              productUpdate.name(),
              categoryDto,
              productUpdate.description(),
              productUpdate.price(),
              productUpdate.stock());

          ProductJpaEntity updatedProductJpaEntity = buildProductJpaEntityFromProductDto(updatedProductDto);
          return ProductMapper.getInstance().fromProductToProductDto(
              ProductMapper.getInstance().fromProductJpaEntityToProduct(
                  productRepository.save(updatedProductJpaEntity)));
        })
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

  }

  private ProductJpaEntity buildProductJpaEntityFromProductDto(ProductDto productDto) {
    Product newProduct = ProductMapper.getInstance().fromProductDtoToProduct(productDto);
    return ProductMapper.getInstance().fromProductToProductJpaEntity(newProduct);
  }
}