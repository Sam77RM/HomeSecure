package com.utp.homesecure.user.domain.valueobjects;

public record PasswordHash(String value) {
    public PasswordHash {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Password hash is required");
        value = value.trim();
    }
}
