package com.domain.shared.valueObjects.cnpj;

import java.util.regex.Pattern;

public record CNPJ(String value) {
    private static Pattern CNPJ_PATTERN = Pattern.compile("^[A-Z0-9]{12}\\d{2}$");

    public CNPJ {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("O CNPJ deve ser informado.");

        value = value.toUpperCase().replaceAll("[^A-Z0-9]", "");

        if (value.length() != 14)
            throw new IllegalArgumentException("O CNPJ deve conter 14 caracteres.");


        if (!CNPJ_PATTERN.matcher(value).matches())
            throw new IllegalArgumentException("O formato do CNPJ é inválido.");
    }

    public String getFormatted() {
        return value.replaceAll("(.{2})(.{3})(.{3})(.{4})(.{2})", "$1.$2.$3/$4-$5");
    }

    @Override
    public String toString() {
        return value;
    }
}
