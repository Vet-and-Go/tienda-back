package com.grupo4.VetAndGo.controller.webmodel.response;

public record ProductResponse(
    Long id,
    String name,
    Long category,
    String description,
    Double price,
    Integer stock) {
}
