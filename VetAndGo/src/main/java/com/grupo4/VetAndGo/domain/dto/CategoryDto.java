package com.grupo4.VetAndGo.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public record CategoryDto(
                Long id,
                @NotEmpty String name,
                String description) {
}
