package com.utp.platform.learning.application.handlers.queries;

import com.utp.platform.learning.application.assemblers.LearningDtoAssembler;
import com.utp.platform.learning.application.dtos.StudentDto;
import com.utp.platform.learning.application.queries.GetStudentQuery;
import com.utp.platform.learning.domain.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetStudentQueryHandler {
    private final StudentRepository students;

    public GetStudentQueryHandler(StudentRepository students) {
        this.students = students;
    }

    public StudentDto handle(GetStudentQuery q) {
        return LearningDtoAssembler.toDto(students.findById(q.id()).orElseThrow(() -> new IllegalArgumentException("Student not found")));
    }
}
