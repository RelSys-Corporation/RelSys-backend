package com.infrastructure.annotations;

import com.domain.system.message.Message;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

public class RequiredFieldValidator implements ConstraintValidator<RequiredField, Object> {
    private Message.Entity entity;

    @Override
    public void initialize(RequiredField constraintAnnotation) {
        this.entity = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isEmpty = switch (value) {
            case null -> true;
            case String s -> s.isBlank();
            case Collection<?> c -> c.isEmpty();
            case Map<?, ?> m -> m.isEmpty();
            case Object[] o -> o.length == 0;
            default -> false;
        };

        if (isEmpty) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    Message.format(Message.Type.REQUIRED_ARGUMENT, this.entity)
            ).addConstraintViolation();
            return false;
        }

        return true;
    }
}
