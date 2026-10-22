package com.utp.homesecure.monitoring.domain.valueobjects;

import java.util.UUID;

/** Monitoring-owned reference to a User account; no dependency on the User aggregate. */
public record OwnerAccountId(String value) {
    public OwnerAccountId {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Owner account id is required");
        try { value = UUID.fromString(value).toString(); }
        catch (IllegalArgumentException ex) { throw new IllegalArgumentException("Owner account id must be a valid UUID", ex); }
    }
}
