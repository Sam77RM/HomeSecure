package com.utp.platform.learning.domain.aggregates;

import com.utp.platform.learning.domain.entities.ProgressRecordItem;
import com.utp.platform.learning.domain.events.EnrollmentConfirmedEvent;
import com.utp.platform.learning.domain.events.EnrollmentRequestedEvent;
import com.utp.platform.learning.domain.valueobjects.EnrollmentStatus;
import com.utp.platform.learning.domain.valueobjects.TutorialId;
import com.utp.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.Instant;
import java.util.*;

public class Enrollment extends AbstractDomainAggregateRoot<Enrollment> {
    private Long id;
    private final Long studentId;
    private final Long courseId;
    private EnrollmentStatus status;
    private final Instant requestedAt;
    private Instant confirmedAt;
    private Instant cancelledAt;
    private final List<ProgressRecordItem> progressItems;

    public Enrollment(Long studentId, Long courseId) {
        this(null, studentId, courseId, EnrollmentStatus.REQUESTED, Instant.now(), null, null, List.of());
        registerDomainEvent(new EnrollmentRequestedEvent(id, studentId, courseId, requestedAt));
    }

    public Enrollment(Long id, Long studentId, Long courseId, EnrollmentStatus status, Instant requestedAt,
                      Instant confirmedAt, Instant cancelledAt, List<ProgressRecordItem> progressItems) {
        this.id = id;
        this.studentId = positive(studentId, "Student id");
        this.courseId = positive(courseId, "Course id");
        if (status == null) throw new IllegalArgumentException("Enrollment status is required");
        if (requestedAt == null) throw new IllegalArgumentException("Requested at is required");
        this.status = status;
        this.requestedAt = requestedAt;
        this.confirmedAt = confirmedAt;
        this.cancelledAt = cancelledAt;
        this.progressItems = new ArrayList<>(progressItems == null ? List.of() : progressItems);
    }

    public void confirm() {
        if (status != EnrollmentStatus.REQUESTED)
            throw new IllegalStateException("Only a requested enrollment can be confirmed");
        status = EnrollmentStatus.CONFIRMED;
        confirmedAt = Instant.now();
        registerDomainEvent(new EnrollmentConfirmedEvent(id, studentId, courseId, confirmedAt));
    }

    public void reject() {
        if (status != EnrollmentStatus.REQUESTED)
            throw new IllegalStateException("Only a requested enrollment can be rejected");
        status = EnrollmentStatus.REJECTED;
    }

    public void cancel() {
        if (status == EnrollmentStatus.CANCELLED || status == EnrollmentStatus.REJECTED)
            throw new IllegalStateException("Enrollment cannot be cancelled in its current status");
        status = EnrollmentStatus.CANCELLED;
        cancelledAt = Instant.now();
    }

    public void initializeProgress(Collection<TutorialId> tutorialIds) {
        if (status != EnrollmentStatus.CONFIRMED)
            throw new IllegalStateException("Progress can only be initialized for a confirmed enrollment");
        if (!progressItems.isEmpty()) throw new IllegalStateException("Progress has already been initialized");
        if (tutorialIds != null)
            tutorialIds.stream().filter(Objects::nonNull).distinct().forEach(id -> progressItems.add(new ProgressRecordItem(id)));
    }

    public void startTutorial(TutorialId tutorialId) {
        progressItem(tutorialId).start();
    }

    public void completeTutorial(TutorialId tutorialId) {
        progressItem(tutorialId).complete();
    }

    public int progressPercentage() {
        if (progressItems.isEmpty()) return 0;
        long done = progressItems.stream().filter(ProgressRecordItem::isCompleted).count();
        return (int) ((done * 100) / progressItems.size());
    }

    private ProgressRecordItem progressItem(TutorialId tutorialId) {
        return progressItems.stream().filter(i -> i.getTutorialId().equals(tutorialId)).findFirst().orElseThrow(() -> new IllegalArgumentException("Tutorial is not part of this enrollment"));
    }

    private static Long positive(Long value, String name) {
        if (value == null || value < 1) throw new IllegalArgumentException(name + " must be positive");
        return value;
    }

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

    public Instant getConfirmedAt() {
        return confirmedAt;
    }

    public Instant getCancelledAt() {
        return cancelledAt;
    }

    public List<ProgressRecordItem> getProgressItems() {
        return Collections.unmodifiableList(progressItems);
    }
}
