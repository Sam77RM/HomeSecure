package com.utp.platform.learning.application.handlers.commands;

import com.utp.platform.learning.application.commands.*;
import com.utp.platform.learning.domain.aggregates.Student;
import com.utp.platform.learning.domain.repositories.StudentRepository;
import com.utp.platform.shared.domain.events.DomainEventDispatcher;
import org.springframework.stereotype.Service;
import com.utp.platform.learning.application.dtos.*;
import com.utp.platform.learning.application.assemblers.LearningDtoAssembler;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentCommandHandler {
    private final StudentRepository repository;
    private final DomainEventDispatcher events;

    public StudentCommandHandler(StudentRepository repository, DomainEventDispatcher events) {
        this.repository = repository;
        this.events = events;
    }

    @Transactional
    public StudentDto handle(CreateStudentCommand c) {
        Student student = new Student(c.firstName(), c.lastName(), c.email());
        Student saved = repository.save(student);
        events.dispatch(student);
        return LearningDtoAssembler.toDto(saved);
    }

    @Transactional
    public StudentDto handle(SetStudentActiveCommand c) {
        Student student = repository.findById(c.studentId()).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        if (c.active()) student.activate();
        else student.deactivate();
        Student saved = repository.update(student);
        events.dispatch(student);
        return LearningDtoAssembler.toDto(saved);
    }
}
