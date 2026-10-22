package com.utp.platform.learning.domain.repositories;
import com.utp.platform.learning.domain.aggregates.Enrollment;
import com.utp.platform.learning.domain.valueobjects.EnrollmentStatus;
import java.util.*;
public interface EnrollmentRepository {
    Enrollment save(Enrollment enrollment);
    Enrollment update(Enrollment enrollment);
    Optional<Enrollment> findById(Long id);
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findByCourseId(Long courseId);
    boolean existsActiveEnrollment(Long studentId, Long courseId);
    long countByCourseIdAndStatus(Long courseId, EnrollmentStatus status);
}
