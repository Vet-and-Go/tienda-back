package com.grupo4.VetAndGo.controller.webmodel.request;

public record ProductRequest(
    Long id,
    String name,
    Long category,
    String description,
    Double price,
    Integer stock) {
}
