package com.utp.platform.learning.domain.events;

import java.time.Instant;

public record EnrollmentConfirmedEvent(Long enrollmentId,
                                       Long studentId, Long courseId, Instant occurredAt) {
}
