package com.utp.platform.learning.domain.events;
import java.time.Instant;
public record CourseCreatedEvent(Long courseId, String code, Instant occurredAt) {}
