package com.domain.shared.valueObjects.barcode;

import jakarta.persistence.AttributeConverter;

public class BarcodeAttributeConverter implements AttributeConverter<Barcode, String> {
    @Override
    public String convertToDatabaseColumn(Barcode barcode) {
        return barcode == null ? null : barcode.value();
    }

    @Override
    public Barcode convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        return new Barcode(dbData);
    }
}
