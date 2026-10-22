package com.utp.platform.learning.application.handlers.commands;

import com.utp.platform.learning.application.commands.*;
import com.utp.platform.shared.domain.events.DomainEventDispatcher;
import com.utp.platform.learning.domain.aggregates.Course;
import com.utp.platform.learning.domain.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import com.utp.platform.learning.application.dtos.*;
import com.utp.platform.learning.application.assemblers.LearningDtoAssembler;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseCommandHandler {
    private final CourseRepository repository;
    private final DomainEventDispatcher events;

    public CourseCommandHandler(CourseRepository repository, DomainEventDispatcher events) {
        this.repository = repository;
        this.events = events;
    }

    @Transactional
    public CourseDto handle(CreateCourseCommand c) {
        if (repository.existsByCode(c.code())) throw new IllegalStateException("Course code already exists");
        Course course = new Course(c.code(), c.title(), c.description(), c.credits(), c.capacity());
        Course saved = repository.save(course);
        events.dispatch(course);
        return LearningDtoAssembler.toDto(saved);
    }

    @Transactional
    public CourseDto handle(UpdateCourseCommand c) {
        Course course = get(c.courseId());
        course.updateDetails(c.title(), c.description(), c.credits(), c.capacity());
        Course saved = repository.update(course);
        events.dispatch(course);
        return LearningDtoAssembler.toDto(saved);
    }

    @Transactional
    public CourseDto handle(SetCourseActiveCommand c) {
        Course course = get(c.courseId());
        if (c.active()) course.activate();
        else course.deactivate();
        Course saved = repository.update(course);
        events.dispatch(course);
        return LearningDtoAssembler.toDto(saved);
    }

    private Course get(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }
}
