package com.utp.platform.learning.infrastructure.persistence.jpa.mappers;

import com.utp.platform.learning.domain.aggregates.Enrollment;
import com.utp.platform.learning.domain.entities.ProgressRecordItem;
import com.utp.platform.learning.domain.valueobjects.TutorialId;
import com.utp.platform.learning.infrastructure.persistence.jpa.entities.CourseJpaEntity;
import com.utp.platform.learning.infrastructure.persistence.jpa.entities.EnrollmentJpaEntity;
import com.utp.platform.learning.infrastructure.persistence.jpa.entities.ProgressRecordItemJpaEntity;
import com.utp.platform.learning.infrastructure.persistence.jpa.entities.StudentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentPersistenceMapper {

    public Enrollment toDomain(EnrollmentJpaEntity e) {
        return new Enrollment(
                e.getId(),
                e.getStudent().getId(),
                e.getCourse().getId(),
                e.getStatus(),
                e.getRequestedAt(),
                e.getConfirmedAt(),
                e.getCancelledAt(),
                e.getProgressItems().stream().map(this::toDomainItem).toList()
        );
    }

    public EnrollmentJpaEntity toEntity(
            Enrollment d,
            StudentJpaEntity student,
            CourseJpaEntity course) {

        EnrollmentJpaEntity e = new EnrollmentJpaEntity();
        e.setId(d.getId());
        e.setStudent(student);
        e.setCourse(course);
        e.setStatus(d.getStatus());
        e.setRequestedAt(d.getRequestedAt());
        e.setConfirmedAt(d.getConfirmedAt());
        e.setCancelledAt(d.getCancelledAt());
        e.replaceProgressItems(
                d.getProgressItems().stream().map(this::toEntityItem).toList()
        );
        return e;
    }

    private ProgressRecordItem toDomainItem(ProgressRecordItemJpaEntity e) {
        return new ProgressRecordItem(
                e.getId(),
                new TutorialId(e.getTutorialId()),
                e.getStatus(),
                e.getStartedAt(),
                e.getCompletedAt()
        );
    }

    private ProgressRecordItemJpaEntity toEntityItem(ProgressRecordItem d) {
        ProgressRecordItemJpaEntity e = new ProgressRecordItemJpaEntity();
        e.setId(d.getId());
        e.setTutorialId(d.getTutorialId().tutorialId());
        e.setStatus(d.getStatus());
        e.setStartedAt(d.getStartedAt());
        e.setCompletedAt(d.getCompletedAt());
        return e;
    }
}
