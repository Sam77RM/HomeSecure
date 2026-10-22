package com.utp.platform.learning.infrastructure.persistence.jpa.mappers;

import com.utp.platform.learning.domain.aggregates.Student;
import com.utp.platform.learning.domain.valueobjects.AcmeStudentRecordId;
import com.utp.platform.learning.infrastructure.persistence.jpa.entities.StudentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class StudentPersistenceMapper {
    public Student toDomain(StudentJpaEntity entity) {
        return new Student(
                entity.getId(),
                new AcmeStudentRecordId(entity.getRecordId()),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.isActive(),
                entity.getCreatedAt()
        );
    }

    public StudentJpaEntity toEntity(Student student) {
        StudentJpaEntity entity = new StudentJpaEntity();
        entity.setId(student.getId());
        entity.setRecordId(student.getRecordId().studentRecordId());
        entity.setFirstName(student.getFirstName());
        entity.setLastName(student.getLastName());
        entity.setEmail(student.getEmail());
        entity.setActive(student.isActive());
        entity.setCreatedAt(student.getCreatedAt());
        return entity;
    }
}
