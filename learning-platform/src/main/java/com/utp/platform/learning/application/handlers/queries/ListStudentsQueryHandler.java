package com.utp.platform.learning.application.handlers.queries;

import com.utp.platform.learning.application.assemblers.LearningDtoAssembler;
import com.utp.platform.learning.application.dtos.StudentDto;
import com.utp.platform.learning.application.queries.ListStudentsQuery;
import com.utp.platform.learning.domain.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ListStudentsQueryHandler {
    private final StudentRepository students;

    public ListStudentsQueryHandler(StudentRepository students) {
        this.students = students;
    }

    public List<StudentDto> handle(ListStudentsQuery q) {
        return students.findAll().stream().map(LearningDtoAssembler::toDto).toList();
    }
}
