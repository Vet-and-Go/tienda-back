package com.grupo4.VetAndGo.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {
  private Long id;
  private String name;
  private Category category;
  private String description;
  private BigDecimal basePrice;
  private Integer stock;
  private BigDecimal discountPercentage;
  private BigDecimal finalPrice;
  private String imageUrl;

  public Product(Long id, String name, Category category, String description, BigDecimal basePrice, Integer stock, BigDecimal discountPercentage, BigDecimal finalPrice, String imageUrl) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.description = description;
    this.basePrice = basePrice;
    this.stock = stock;
    this.discountPercentage = discountPercentage;
    this.finalPrice = finalPrice != null ? finalPrice : calculateFinalPrice();
    this.imageUrl = imageUrl;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getBasePrice() {
    return basePrice;
  }

  public void setBasePrice(BigDecimal basePrice) {
    this.basePrice = basePrice;
    this.finalPrice = calculateFinalPrice();
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public BigDecimal getDiscountPercentage() {
    return discountPercentage;
  }

  public void setDiscountPercentage(BigDecimal discountPercentage) {
    this.discountPercentage = discountPercentage;
    this.finalPrice = calculateFinalPrice();
  }

  public BigDecimal getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(BigDecimal finalPrice) {
    this.finalPrice = finalPrice;
  }

  public BigDecimal calculateFinalPrice() {
    if (basePrice == null) {
      return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }
    if (discountPercentage == null || discountPercentage.compareTo(BigDecimal.ZERO) == 0) {
      return basePrice.setScale(2, RoundingMode.HALF_UP);
    }

    BigDecimal discount = basePrice
        .multiply(discountPercentage)
        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

    return basePrice.subtract(discount).setScale(2, RoundingMode.HALF_UP);
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
