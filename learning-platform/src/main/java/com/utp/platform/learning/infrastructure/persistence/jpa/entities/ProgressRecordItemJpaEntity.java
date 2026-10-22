package com.utp.platform.learning.infrastructure.persistence.jpa.entities;

import com.utp.platform.learning.domain.valueobjects.ProgressStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "enrollment_progress", uniqueConstraints = @UniqueConstraint(name = "uk_enrollment_tutorial", columnNames = {"enrollment_id", "tutorial_id"}))
public class ProgressRecordItemJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private EnrollmentJpaEntity enrollment;
    @Column(name = "tutorial_id", nullable = false)
    private Long tutorialId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProgressStatus status;
    @Column(name = "started_at")
    private Instant startedAt;
    @Column(name = "completed_at")
    private Instant completedAt;
}
