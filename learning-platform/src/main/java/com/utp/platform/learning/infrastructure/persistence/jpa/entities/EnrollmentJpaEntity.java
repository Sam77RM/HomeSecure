package com.utp.platform.learning.infrastructure.persistence.jpa.entities;

import com.utp.platform.learning.domain.valueobjects.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrollments", indexes = {@Index(name = "ix_enrollment_student", columnList = "student_id"), @Index(name = "ix_enrollment_course", columnList = "course_id")})
public class EnrollmentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentJpaEntity student;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseJpaEntity course;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EnrollmentStatus status;
    @Column(name = "requested_at", nullable = false, updatable = false)
    private Instant requestedAt;
    @Column(name = "confirmed_at")
    private Instant confirmedAt;
    @Column(name = "cancelled_at")
    private Instant cancelledAt;
    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProgressRecordItemJpaEntity> progressItems = new ArrayList<>();

    public void replaceProgressItems(List<ProgressRecordItemJpaEntity> items) {
        progressItems.clear();
        if (items != null) {
            items.forEach(i -> i.setEnrollment(this));
            progressItems.addAll(items);
        }
    }
}
