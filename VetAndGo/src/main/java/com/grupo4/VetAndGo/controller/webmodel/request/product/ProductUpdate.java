package com.grupo4.VetAndGo.controller.webmodel.request.product;

public record ProductUpdate(
    Long id,
    String name,
    Long category,
    String description,
    Double price,
    Integer stock) {
}
