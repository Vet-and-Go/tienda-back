package com.grupo4.VetAndGo.controller.webmodel.response;


import com.grupo4.VetAndGo.controller.webmodel.response.CategoryResponse;

public record ProductResponse(
    Long id,
    String name,
    CategoryResponse category,
    String description,
    Double price,
    Integer stock) {
}
