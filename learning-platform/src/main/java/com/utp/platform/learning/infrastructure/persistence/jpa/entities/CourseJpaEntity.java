package com.utp.platform.learning.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses", uniqueConstraints = @UniqueConstraint(name = "uk_course_code", columnNames = "code"))
public class CourseJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    private String code;
    @Column(nullable = false, length = 150)
    private String title;
    @Column(length = 1000)
    private String description;
    @Column(nullable = false)
    private int credits;
    @Column(nullable = false)
    private int capacity;
    @Column(nullable = false)
    private boolean active;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "course_prerequisites", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "prerequisite_course_id", nullable = false)
    private Set<Long> prerequisiteCourseIds = new HashSet<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "course_tutorials", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "tutorial_id", nullable = false)
    @OrderColumn(name = "tutorial_order")
    private List<Long> tutorialIds = new ArrayList<>();
}
