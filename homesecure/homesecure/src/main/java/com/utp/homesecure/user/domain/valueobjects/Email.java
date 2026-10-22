package com.utp.homesecure.user.domain.valueobjects;

public record Email(String value) {
    public Email {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Email is required");
        value = value.trim();
        if (!value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
            throw new IllegalArgumentException("Email format is invalid");
    }
}
