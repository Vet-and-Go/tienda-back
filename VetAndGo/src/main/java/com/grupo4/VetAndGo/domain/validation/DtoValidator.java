package com.grupo4.VetAndGo.domain.validation;

import jakarta.validation.*;

import java.util.Set;
import java.util.stream.Collectors;

public class DtoValidator {
    private static Validator validator;

    private static Validator getValidator() {
        if (validator == null) {
            ValidatorFactory factory = Validation.byDefaultProvider()
                    .configure()
                    .buildValidatorFactory();
            validator = factory.getValidator();
        }
        return validator;
    }

    public static <T> void validate(T dto) {
        Set<ConstraintViolation<T>> violations = getValidator().validate(dto);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .collect(Collectors.joining(", "));
            throw new ValidationException(errorMessage);
        }
    }
}
