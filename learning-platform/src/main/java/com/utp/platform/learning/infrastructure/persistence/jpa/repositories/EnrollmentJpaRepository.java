package com.utp.platform.learning.infrastructure.persistence.jpa.repositories;

import com.utp.platform.learning.domain.valueobjects.EnrollmentStatus;
import com.utp.platform.learning.infrastructure.persistence.jpa.entities.EnrollmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentJpaEntity, Long> {
    List<EnrollmentJpaEntity> findByStudent_Id(Long studentId);

    List<EnrollmentJpaEntity> findByCourse_Id(Long courseId);

    boolean existsByStudent_IdAndCourse_IdAndStatusIn(Long studentId, Long courseId, Collection<EnrollmentStatus> statuses);

    long countByCourse_IdAndStatus(Long courseId, EnrollmentStatus status);
}
