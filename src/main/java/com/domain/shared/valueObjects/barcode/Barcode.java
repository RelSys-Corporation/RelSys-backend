package com.domain.shared.valueObjects.barcode;

import com.domain.system.message.Message;

import java.util.UUID;

public record Barcode(String value) {
    public Barcode {
        if (value == null || value.isBlank()) {
            value = generateStarterBarcode();
        }

        if (!value.startsWith("TEMP")) {
            if (value.length() != 13) {
                throw new IllegalArgumentException(Message.format(Message.Type.SHOULD_HAVE, Message.Entity.BARCODE, "mais de 13 carcaters"));
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
            throw new IllegalStateException(Message.format(Message.Type.IDENTIFIER_ABSENT, Message.Entity.BARCODE));

        return new Barcode(String.format("%013d", id));
    }

    @Override
    public String toString() {
        return value;
    }
}
