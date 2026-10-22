package com.utp.homesecure.user.domain.valueobjects;

import java.util.UUID;

public record UserAccountId(String userAccountId) {
    public UserAccountId() { this(UUID.randomUUID().toString()); }

    public UserAccountId {
        if (userAccountId == null || userAccountId.isBlank())
            throw new IllegalArgumentException("User account id cannot be null or empty");
        try { UUID.fromString(userAccountId); }
        catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("User account id must be a valid UUID");
        }
    }
}
