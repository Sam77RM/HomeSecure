package com.utp.platform.learning.domain.entities;

import com.utp.platform.learning.domain.valueobjects.ProgressStatus;
import com.utp.platform.learning.domain.valueobjects.TutorialId;

import java.time.Instant;

public class ProgressRecordItem {
    private Long id;
    private final TutorialId tutorialId;
    private ProgressStatus status;
    private Instant startedAt;
    private Instant completedAt;

    public ProgressRecordItem(TutorialId tutorialId) {
        this(null, tutorialId, ProgressStatus.NOT_STARTED, null, null);
    }

    public ProgressRecordItem(Long id, TutorialId tutorialId, ProgressStatus status, Instant startedAt, Instant completedAt) {
        if (tutorialId == null) throw new IllegalArgumentException("Tutorial id is required");
        if (status == null) throw new IllegalArgumentException("Progress status is required");
        this.id = id;
        this.tutorialId = tutorialId;
        this.status = status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
    }

    public void start() {
        if (status != ProgressStatus.NOT_STARTED) throw new IllegalStateException("Tutorial can only be started once");
        status = ProgressStatus.STARTED;
        startedAt = Instant.now();
    }

    public void complete() {
        if (status != ProgressStatus.STARTED)
            throw new IllegalStateException("Tutorial must be started before completion");
        status = ProgressStatus.COMPLETED;
        completedAt = Instant.now();
    }

    public boolean isCompleted() {
        return status == ProgressStatus.COMPLETED;
    }

    public Long getId() {
        return id;
    }

    public TutorialId getTutorialId() {
        return tutorialId;
    }

    public ProgressStatus getStatus() {
        return status;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }
}
