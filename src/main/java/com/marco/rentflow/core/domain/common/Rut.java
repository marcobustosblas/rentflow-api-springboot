package com.marco.rentflow.core.domain.common;

public record Rut(String value) {
    public Rut {
        if (value.isBlank() || !value.matches("^[0-9]{7,8}-[0-9kK]{1}$")) {
            throw new IllegalArgumentException("El RUT ingresado no es válido: " + value);
        }
    }
}
