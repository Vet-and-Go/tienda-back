package com.grupo4.VetAndGo.domain.exception;

import jakarta.validation.ConstraintViolation;

import java.util.Set;
import java.util.stream.Collectors;

public class ValidationException extends RuntimeException {
    private final Set<ConstraintViolation<?>> violations;

    public ValidationException(String message) {
        super(message);
        this.violations = Set.of();
    }

    public ValidationException(Set<? extends ConstraintViolation<?>> violations) {
        super("Errores de validación detectados: " + violations.size());
        this.violations = Set.copyOf(violations);
    }

    public Set<ConstraintViolation<?>> getViolations() {
        return violations;
    }

    @Override
    public String getMessage() {
        if (violations.isEmpty()) {
            return super.getMessage();
        }
        return violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining(", "));
    }
}
