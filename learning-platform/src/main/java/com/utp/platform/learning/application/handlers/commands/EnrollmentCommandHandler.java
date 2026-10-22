package com.utp.platform.learning.application.handlers.commands;

import com.utp.platform.learning.application.commands.*;
import com.utp.platform.shared.domain.events.DomainEventDispatcher;
import com.utp.platform.learning.domain.aggregates.*;
import com.utp.platform.learning.domain.repositories.*;
import com.utp.platform.learning.domain.valueobjects.*;
import org.springframework.stereotype.Service;
import com.utp.platform.learning.application.dtos.*;
import com.utp.platform.learning.application.assemblers.LearningDtoAssembler;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentCommandHandler {
    private final EnrollmentRepository enrollments;
    private final StudentRepository students;
    private final CourseRepository courses;
    private final DomainEventDispatcher events;

    public EnrollmentCommandHandler(EnrollmentRepository enrollments, StudentRepository students, CourseRepository courses, DomainEventDispatcher events) {
        this.enrollments = enrollments;
        this.students = students;
        this.courses = courses;
        this.events = events;
    }

    @Transactional
    public EnrollmentDto handle(RequestEnrollmentCommand c) {
        Student student = students.findById(c.studentId()).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Course course = courses.findById(c.courseId()).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        if (!student.isActive() || !course.isActive())
            throw new IllegalStateException("Student and course must be active");
        if (enrollments.existsActiveEnrollment(c.studentId(), c.courseId()))
            throw new IllegalStateException("Active enrollment already exists");
        Enrollment enrollment = new Enrollment(c.studentId(), c.courseId());
        Enrollment saved = enrollments.save(enrollment);
        events.dispatch(enrollment);
        return LearningDtoAssembler.toDto(saved);
    }

    @Transactional
    public EnrollmentDto handle(ConfirmEnrollmentCommand c) {
        Enrollment e = get(c.enrollmentId());
        Course course = courses.findById(e.getCourseId()).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Student student = students.findById(e.getStudentId()).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        if (!student.isActive() || !course.isActive())
            throw new IllegalStateException("Student and course must be active");
        if (!course.hasCapacity((int) enrollments.countByCourseIdAndStatus(course.getId(), EnrollmentStatus.CONFIRMED)))
            throw new IllegalStateException("Course capacity exceeded");
        e.confirm();
        e.initializeProgress(course.getTutorialIds());
        return persist(e);
    }

    @Transactional
    public EnrollmentDto handle(RejectEnrollmentCommand c) {
        Enrollment e = get(c.enrollmentId());
        e.reject();
        return persist(e);
    }

    @Transactional
    public EnrollmentDto handle(CancelEnrollmentCommand c) {
        Enrollment e = get(c.enrollmentId());
        e.cancel();
        return persist(e);
    }

    @Transactional
    public EnrollmentDto handle(StartTutorialCommand c) {
        Enrollment e = get(c.enrollmentId());
        e.startTutorial(new TutorialId(c.tutorialId()));
        return persist(e);
    }

    @Transactional
    public EnrollmentDto handle(CompleteTutorialCommand c) {
        Enrollment e = get(c.enrollmentId());
        e.completeTutorial(new TutorialId(c.tutorialId()));
        return persist(e);
    }

    private Enrollment get(Long id) {
        return enrollments.findById(id).orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
    }

    private EnrollmentDto persist(Enrollment e) {
        Enrollment saved = enrollments.update(e);
        events.dispatch(e);
        return LearningDtoAssembler.toDto(saved);
    }
}
