package com.domain.shared.valueObjects.cpf;

import com.domain.system.message.Message;

public record CPF(String value) {
    public CPF {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException(Message.format(Message.Type.REQUIRED_ARGUMENT, Message.Entity.CPF));

        value = value.toUpperCase().replaceAll("[^0-9]", "");

        if (value.length() != 11)
            throw new IllegalArgumentException(Message.format(Message.Type.SHOULD_HAVE, Message.Entity.CPF, "11 caracteres"));
    }

    public String getFormatted() {
        return value.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    @Override
    public String toString() {
        return value;
    }
}
