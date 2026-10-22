package com.utp.platform.learning.application.dtos;

public record ProgressItemDto(Long id,
                              Long tutorialId,
                              com.utp.platform.learning.domain.valueobjects.ProgressStatus status,
                              java.time.Instant startedAt,
                              java.time.Instant completedAt) {}
