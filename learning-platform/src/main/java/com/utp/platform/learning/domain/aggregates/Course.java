package com.utp.platform.learning.domain.aggregates;

import com.utp.platform.learning.domain.events.CourseCreatedEvent;
import com.utp.platform.learning.domain.valueobjects.TutorialId;
import com.utp.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.Instant;
import java.util.*;

public class Course extends AbstractDomainAggregateRoot<Course> {
    private Long id;
    private final String code;
    private String title;
    private String description;
    private int credits;
    private int capacity;
    private boolean active;
    private final Instant createdAt;
    private final Set<Long> prerequisiteCourseIds;
    private final List<TutorialId> tutorialIds;

    public Course(String code, String title, String description, int credits, int capacity) {
        this(null, code, title, description, credits, capacity, true, Instant.now(), Set.of(), List.of());
        registerDomainEvent(new CourseCreatedEvent(id, code, Instant.now()));
    }

    public Course(Long id, String code, String title, String description, int credits,
                  int capacity, boolean active, Instant createdAt,
                  Set<Long> prerequisiteCourseIds, List<TutorialId> tutorialIds) {
        this.id = id;
        this.code = required(code, "Course code").toUpperCase(Locale.ROOT);
        this.title = required(title, "Course title");
        this.description = description == null ? "" : description.trim();
        validateCredits(credits);
        validateCapacity(capacity);
        if (createdAt == null) throw new IllegalArgumentException("Created at is required");
        this.credits = credits;
        this.capacity = capacity;
        this.active = active;
        this.createdAt = createdAt;
        this.prerequisiteCourseIds = new HashSet<>(prerequisiteCourseIds == null ? Set.of() : prerequisiteCourseIds);
        this.tutorialIds = new ArrayList<>(tutorialIds == null ? List.of() : tutorialIds);
    }

    public void updateDetails(String title, String description, int credits, int capacity) {
        this.title = required(title, "Course title");
        this.description = description == null ? "" : description.trim();
        validateCredits(credits);
        validateCapacity(capacity);
        this.credits = credits;
        this.capacity = capacity;
    }

    public void activate() {
        if (active) throw new IllegalStateException("Course is already active");
        active = true;
    }

    public void deactivate() {
        if (!active) throw new IllegalStateException("Course is already inactive");
        active = false;
    }

    public void addPrerequisite(Long courseId) {
        if (courseId == null || courseId < 1)
            throw new IllegalArgumentException("Prerequisite course id must be positive");
        if (Objects.equals(id, courseId)) throw new IllegalArgumentException("A course cannot be its own prerequisite");
        prerequisiteCourseIds.add(courseId);
    }

    public void removePrerequisite(Long courseId) {
        prerequisiteCourseIds.remove(courseId);
    }

    public void addTutorial(TutorialId tutorialId) {
        if (tutorialId == null) throw new IllegalArgumentException("Tutorial id is required");
        if (!tutorialIds.contains(tutorialId)) tutorialIds.add(tutorialId);
    }

    public void removeTutorial(TutorialId tutorialId) {
        tutorialIds.remove(tutorialId);
    }

    public boolean hasCapacity(int confirmedEnrollments) {
        return confirmedEnrollments >= 0 && confirmedEnrollments < capacity;
    }

    private static void validateCredits(int value) {
        if (value < 1 || value > 30) throw new IllegalArgumentException("Credits must be between 1 and 30");
    }

    private static void validateCapacity(int value) {
        if (value < 1) throw new IllegalArgumentException("Capacity must be greater than zero");
    }

    private static String required(String value, String name) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(name + " is required");
        return value.trim();
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCredits() {
        return credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Set<Long> getPrerequisiteCourseIds() {
        return Collections.unmodifiableSet(prerequisiteCourseIds);
    }

    public List<TutorialId> getTutorialIds() {
        return Collections.unmodifiableList(tutorialIds);
    }
}
