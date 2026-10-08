package com.domain.shared.valueObjects.cpf;

public record CPF(String value) {
    public CPF {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("O CPF deve ser informado.");

        value = value.toUpperCase().replaceAll("[^0-9]", "");

        if (value.length() != 11)
            throw new IllegalArgumentException("O CPF deve conter 11 caracteres.");
    }

    public String getFormatted() {
        return value.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    @Override
    public String toString() {
        return value;
    }
}
