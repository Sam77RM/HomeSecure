package com.utp.platform.learning.domain.events;
import java.time.Instant;
public record EnrollmentRequestedEvent(Long enrollmentId, Long studentId,
                                       Long courseId, Instant occurredAt) {}
