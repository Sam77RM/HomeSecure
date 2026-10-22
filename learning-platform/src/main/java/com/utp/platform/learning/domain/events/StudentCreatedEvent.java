package com.utp.platform.learning.domain.events;

import java.time.Instant;

public record StudentCreatedEvent(
        Long studentId,
        String studentRecordId,
        String email,
        Instant occurredOn
) {
}
