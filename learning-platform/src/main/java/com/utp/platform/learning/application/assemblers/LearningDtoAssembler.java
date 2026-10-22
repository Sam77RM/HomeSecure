package com.utp.platform.learning.application.assemblers;

import com.utp.platform.learning.application.dtos.*;
import com.utp.platform.learning.domain.aggregates.*;
import com.utp.platform.learning.domain.entities.ProgressRecordItem;
import com.utp.platform.learning.domain.valueobjects.TutorialId;

/** Converts domain models to immutable application results; no HTTP dependency. */
public final class LearningDtoAssembler {
    private LearningDtoAssembler() {}

    public static StudentDto toDto(Student s) {
        return new StudentDto(s.getId(),
                s.getRecordId().studentRecordId(),
                s.getFirstName(),
                s.getLastName(),
                s.getEmail(),
                s.isActive(),
                s.getCreatedAt());
    }
    public static CourseDto toDto(Course c) {
        return new CourseDto(c.getId(),
                c.getCode(),
                c.getTitle(),
                c.getDescription(),
                c.getCredits(),
                c.getCapacity(),
                c.isActive(),
                c.getCreatedAt(),
                java.util.Set.copyOf(c.getPrerequisiteCourseIds()),
                c.getTutorialIds().stream().map(TutorialId::tutorialId).toList());
    }
    public static ProgressItemDto toDto(ProgressRecordItem i) {
        return new ProgressItemDto(i.getId(),
                i.getTutorialId().tutorialId(),
                i.getStatus(),
                i.getStartedAt(),
                i.getCompletedAt());
    }
    public static EnrollmentDto toDto(Enrollment e) {
        return new EnrollmentDto(e.getId(), e.getStudentId(), e.getCourseId(), e.getStatus(),
                e.getRequestedAt(), e.getConfirmedAt(), e.getCancelledAt(),
                e.progressPercentage(), e.getProgressItems().stream().map(LearningDtoAssembler::toDto).toList());
    }
}
