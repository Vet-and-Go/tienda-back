package com.grupo4.VetAndGo.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class OrderItem {
  private Long id;
  private Product product;
  private Integer quantity;
  private BigDecimal unitPrice;
  private BigDecimal subtotal;

  public OrderItem(Long id, Product product, Integer quantity, BigDecimal unitPrice, BigDecimal subtotal) {
    this.id = id;
    this.product = Objects.requireNonNull(product, "Product cannot be null");
    setQuantity(quantity);
    setUnitPrice(unitPrice);
    this.subtotal = subtotal != null ? subtotal : calculateSubtotal();
  }

  public static OrderItem create(Product product, Integer quantity) {
    Objects.requireNonNull(product, "Product cannot be null");
    if (quantity == null || quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than 0");
    }
    BigDecimal unitPrice = product.getFinalPrice();
    return new OrderItem(null, product, quantity, unitPrice, null);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = Objects.requireNonNull(product, "Product cannot be null");
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    if (quantity != null && quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than 0");
    }
    this.quantity = quantity;
    this.subtotal = calculateSubtotal();
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    if (unitPrice != null && unitPrice.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Unit price cannot be negative");
    }
    this.unitPrice = unitPrice;
    this.subtotal = calculateSubtotal();
  }

  public BigDecimal getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(BigDecimal subtotal) {
    this.subtotal = subtotal;
  }

  public BigDecimal calculateSubtotal() {
    if (unitPrice == null || quantity == null) {
      return BigDecimal.ZERO;
    }
    return unitPrice.multiply(BigDecimal.valueOf(quantity))
        .setScale(2, RoundingMode.HALF_UP);
  }

  public void increaseQuantity(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0");
    }
    setQuantity(this.quantity + amount);
  }

  public void decreaseQuantity(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0");
    }
    if (this.quantity - amount <= 0) {
      throw new IllegalArgumentException("Resulting quantity would be zero or negative");
    }
    setQuantity(this.quantity - amount);
  }

  public boolean isSameProduct(Product otherProduct) {
    return this.product != null 
        && otherProduct != null 
        && this.product.getId() != null
        && this.product.getId().equals(otherProduct.getId());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OrderItem)) return false;
    OrderItem orderItem = (OrderItem) o;
    return Objects.equals(id, orderItem.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "OrderItem{" +
        "id=" + id +
        ", product=" + (product != null ? product.getName() : "null") +
        ", quantity=" + quantity +
        ", unitPrice=" + unitPrice +
        ", subtotal=" + subtotal +
        '}';
  }
}
