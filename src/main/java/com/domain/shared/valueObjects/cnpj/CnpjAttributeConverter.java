package com.domain.shared.valueObjects.cnpj;

import jakarta.persistence.AttributeConverter;

public class CnpjAttributeConverter implements AttributeConverter<CNPJ, String> {
    @Override
    public String convertToDatabaseColumn(CNPJ cnpj) {
        return cnpj == null ? null : cnpj.value();
    }

    @Override
    public CNPJ convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        return new CNPJ(dbData);
    }
}
