package com.domain.system.company;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CompanyIdAttributeConverter implements AttributeConverter<String, Long> {
    @Override
    public Long convertToDatabaseColumn(String attribute) {
        if (attribute == null || attribute.isBlank())
            return null;

        try {
            return Long.parseLong(attribute);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    @Override
    public String convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : dbData.toString();
    }
}
