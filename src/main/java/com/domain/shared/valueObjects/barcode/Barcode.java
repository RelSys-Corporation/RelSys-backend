package com.domain.shared.valueObjects.barcode;

import java.util.UUID;

public record Barcode(String value) {
    public Barcode {
        if (value == null || value.isBlank()) {
            value = generateStarterBarcode();
        }

        if (!value.startsWith("TEMP")) {
            if (value.length() != 13) {
                throw new IllegalArgumentException("O código de barras informado deve possuir 13 caracteres.");
            }
        }
    }

    public boolean isTemporary() {
        return value.startsWith("TEMP");
    }

    private String generateStarterBarcode() {
        return ("TEMP-" + UUID.randomUUID().toString().toUpperCase());
    }

    public static Barcode generateBarcode(Long id) {
        if (id == null)
            throw new IllegalStateException("O ID deve ser informado para geração do código de barras.");
        return new Barcode(String.format("%013d", id));
    }

    @Override
    public String toString() {
        return value;
    }
}
