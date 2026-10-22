package com.utp.platform.learning.application.handlers.queries;

import com.utp.platform.learning.application.assemblers.LearningDtoAssembler;
import com.utp.platform.learning.application.dtos.CourseDto;
import com.utp.platform.learning.application.queries.ListCoursesQuery;
import com.utp.platform.learning.domain.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ListCoursesQueryHandler {
    private final CourseRepository courses;

    public ListCoursesQueryHandler(CourseRepository courses) {
        this.courses = courses;
    }

    public List<CourseDto> handle(ListCoursesQuery q) {
        return courses.findAll().stream().map(LearningDtoAssembler::toDto).toList();
    }
}
