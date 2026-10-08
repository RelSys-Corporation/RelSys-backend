package com.domain.shared.valueObjects.cpf;

import jakarta.persistence.AttributeConverter;

public class CpfAttributeConverter implements AttributeConverter<CPF, String> {
    @Override
    public String convertToDatabaseColumn(CPF cpf) {
        return cpf == null ? null : cpf.value();
    }

    @Override
    public CPF convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        return new CPF(dbData);
    }
}
