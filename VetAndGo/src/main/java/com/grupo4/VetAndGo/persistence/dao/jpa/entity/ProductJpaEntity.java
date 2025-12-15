package com.grupo4.VetAndGo.persistence.dao.jpa.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "products")
public class ProductJpaEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name")
  private String name;

  /*
   * @ManyToOne(fetch = FetchType.LAZY)
   * 
   * @JoinColumn(name = "category_id")
   * private CategoryJpaEntity category;
   */

  @Column(name = "description")
  private String description;
  @Column(name = "price")
  private Double price;
  @Column(name = "stock")
  private Integer stock;
  @Column(name = "category_id")
  private Long category;

  public ProductJpaEntity() {
  }

  public ProductJpaEntity(Long id, String name, Long category, String description, Double price,
      Integer stock) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.description = description;
    this.price = price;
    this.stock = stock;
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

  /*
   * public CategoryJpaEntity getCategory() {
   * return category;
   * }
   * 
   * public void setCategory(CategoryJpaEntity category) {
   * this.category = category;
   * }
   */

  public Long getCategory() {
    return category;
  }

  public void setCategory(Long category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

}
