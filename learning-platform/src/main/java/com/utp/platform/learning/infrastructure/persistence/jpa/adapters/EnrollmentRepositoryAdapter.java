package com.utp.platform.learning.infrastructure.persistence.jpa.adapters;

import com.utp.platform.learning.domain.aggregates.Enrollment;
import com.utp.platform.learning.domain.repositories.EnrollmentRepository;
import com.utp.platform.learning.domain.valueobjects.EnrollmentStatus;
import com.utp.platform.learning.infrastructure.persistence.jpa.entities.CourseJpaEntity;
import com.utp.platform.learning.infrastructure.persistence.jpa.entities.EnrollmentJpaEntity;
import com.utp.platform.learning.infrastructure.persistence.jpa.entities.StudentJpaEntity;
import com.utp.platform.learning.infrastructure.persistence.jpa.mappers.EnrollmentPersistenceMapper;
import com.utp.platform.learning.infrastructure.persistence.jpa.repositories.CourseJpaRepository;
import com.utp.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentJpaRepository;
import com.utp.platform.learning.infrastructure.persistence.jpa.repositories.StudentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EnrollmentRepositoryAdapter implements EnrollmentRepository {
    private final EnrollmentJpaRepository repo;
    private final EnrollmentPersistenceMapper mapper;
    private final StudentJpaRepository studentRepo;
    private final CourseJpaRepository courseRepo;

    public EnrollmentRepositoryAdapter(
            EnrollmentJpaRepository repo,
            EnrollmentPersistenceMapper mapper,
            StudentJpaRepository studentRepo,
            CourseJpaRepository courseRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        StudentJpaEntity student = studentRepo.findById(enrollment.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + enrollment.getStudentId()));
        CourseJpaEntity course = courseRepo.findById(enrollment.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + enrollment.getCourseId()));
        EnrollmentJpaEntity entity = mapper.toEntity(enrollment, student, course);
        return mapper.toDomain(repo.save(entity));
    }

    @Override
    public Enrollment update(Enrollment enrollment) {
        return save(enrollment);
    }

    @Override
    public Optional<Enrollment> findById(Long id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Enrollment> findByStudentId(Long id) {
        return repo.findByStudent_Id(id).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Enrollment> findByCourseId(Long id) {
        return repo.findByCourse_Id(id).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsActiveEnrollment(Long studentId, Long courseId) {
        return repo.existsByStudent_IdAndCourse_IdAndStatusIn(
                studentId, courseId, List.of(EnrollmentStatus.REQUESTED, EnrollmentStatus.CONFIRMED));
    }

    @Override
    public long countByCourseIdAndStatus(Long courseId, EnrollmentStatus status) {
        return repo.countByCourse_IdAndStatus(courseId, status);
    }
}
