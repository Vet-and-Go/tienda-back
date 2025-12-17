package com.grupo4.VetAndGo.controller.webmodel.request.product;

public record ProductInsert(
    String name,
    Long category,
    String description,
    Double price,
    Integer stock) {
}
