package com.utp.platform.learning.application.dtos;

public record CourseDto(Long id, String code,
                        String title,
                        String description,
                        int credits,
                        int capacity,
                        boolean active,
                        java.time.Instant createdAt,
                        java.util.Set<Long> prerequisiteCourseIds,
                        java.util.List<Long> tutorialIds) {
}
