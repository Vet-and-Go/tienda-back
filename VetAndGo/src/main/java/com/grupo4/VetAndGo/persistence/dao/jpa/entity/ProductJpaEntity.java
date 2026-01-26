package com.grupo4.VetAndGo.persistence.dao.jpa.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductJpaEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id")
  private CategoryJpaEntity category;

  @Column(name = "description")
  private String description;
  @Column(name = "base_price")
  private BigDecimal basePrice;
  @Column(name = "stock")
  private Integer stock;

  @Column(name = "discount_percentage")
  private BigDecimal discountPercentage;

  @Column(name = "final_price")
  private BigDecimal finalPrice;

  @Column(name = "image_url")
  private String imageUrl;

  public ProductJpaEntity() {
  }

  public ProductJpaEntity(Long id, String name, CategoryJpaEntity category, String description, BigDecimal basePrice,
      Integer stock, BigDecimal discountPercentage, BigDecimal finalPrice, String imageUrl) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.description = description;
    this.basePrice = basePrice;
    this.stock = stock;
    this.discountPercentage = discountPercentage;
    this.finalPrice = finalPrice;
    this.imageUrl = imageUrl;
  }

  // Constructor de conveniencia para compatibilidad con tests (sin descuento)
  public ProductJpaEntity(Long id, String name, CategoryJpaEntity category, String description, BigDecimal basePrice,
      Integer stock, String imageUrl) {
    this(id, name, category, description, basePrice, stock, BigDecimal.ZERO, basePrice, imageUrl);
  }

  // Constructor de conveniencia para compatibilidad con tests (precio como double)
  public ProductJpaEntity(Long id, String name, CategoryJpaEntity category, String description, double basePrice,
      Integer stock, String imageUrl) {
    this(id, name, category, description, BigDecimal.valueOf(basePrice), stock, BigDecimal.ZERO, BigDecimal.valueOf(basePrice), imageUrl);
  }

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

  public CategoryJpaEntity getCategory() {
    return category;
  }

  public void setCategory(CategoryJpaEntity category) {
    this.category = category;
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
  }

  public BigDecimal getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(BigDecimal finalPrice) {
    this.finalPrice = finalPrice;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

}
