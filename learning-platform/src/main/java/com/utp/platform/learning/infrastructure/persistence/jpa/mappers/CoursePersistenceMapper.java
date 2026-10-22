package com.utp.platform.learning.infrastructure.persistence.jpa.mappers;

import com.utp.platform.learning.domain.aggregates.Course;
import com.utp.platform.learning.domain.valueobjects.TutorialId;
import com.utp.platform.learning.infrastructure.persistence.jpa.entities.CourseJpaEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CoursePersistenceMapper {
    public Course toDomain(CourseJpaEntity e) {
        return new Course(e.getId(), e.getCode(),
                e.getTitle(), e.getDescription(),
                e.getCredits(),
                e.getCapacity(),
                e.isActive(),
                e.getCreatedAt(),
                e.getPrerequisiteCourseIds(),
                e.getTutorialIds().stream().map(TutorialId::new).toList());
    }

    public CourseJpaEntity toEntity(Course c) {
        CourseJpaEntity e = new CourseJpaEntity();
        e.setId(c.getId());
        e.setCode(c.getCode());
        e.setTitle(c.getTitle());
        e.setDescription(c.getDescription());
        e.setCredits(c.getCredits());
        e.setCapacity(c.getCapacity());
        e.setActive(c.isActive());
        e.setCreatedAt(c.getCreatedAt());
        e.setPrerequisiteCourseIds(new HashSet<>(c.getPrerequisiteCourseIds()));
        e.setTutorialIds(c.getTutorialIds().stream().map(TutorialId::tutorialId).toList());
        return e;
    }
}
