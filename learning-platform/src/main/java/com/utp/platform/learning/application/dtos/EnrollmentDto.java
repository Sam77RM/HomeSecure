package com.utp.platform.learning.application.dtos;

public record EnrollmentDto(
        Long id,
        Long studentId,
        Long courseId,
        com.utp.platform.learning.domain.valueobjects.EnrollmentStatus status,
        java.time.Instant requestedAt,
        java.time.Instant confirmedAt,
        java.time.Instant cancelledAt,
        int progressPercentage,
        java.util.List<ProgressItemDto> progressItems) {}
