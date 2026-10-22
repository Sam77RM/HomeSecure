package com.utp.platform.learning.domain.entities;

import com.utp.platform.learning.domain.valueobjects.TutorialId;

public class LearningPathItem {
    private Long id;
    private final Long courseId;
    private final TutorialId tutorialId;
    private Integer position;

    public LearningPathItem(Long courseId, TutorialId tutorialId, int position) {
        this(null, courseId, tutorialId, position);
    }

    public LearningPathItem(Long id, Long courseId, TutorialId tutorialId, int position) {
        if (courseId == null || courseId < 1) throw new IllegalArgumentException("Course id must be positive");
        if (tutorialId == null) throw new IllegalArgumentException("Tutorial id is required");
        if (position < 1) throw new IllegalArgumentException("Position must be positive");
        this.id = id;
        this.courseId = courseId;
        this.tutorialId = tutorialId;
        this.position = position;
    }

    public void changePosition(int position) {
        if (position < 1) throw new IllegalArgumentException("Position must be positive");
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public TutorialId getTutorialId() {
        return tutorialId;
    }

    public Integer getPosition() {
        return position;
    }
}
