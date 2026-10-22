package com.utp.platform.learning.application.handlers.queries;

import com.utp.platform.learning.application.assemblers.LearningDtoAssembler;
import com.utp.platform.learning.application.dtos.CourseDto;
import com.utp.platform.learning.application.queries.GetCourseQuery;
import com.utp.platform.learning.domain.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetCourseQueryHandler {
    private final CourseRepository courses;

    public GetCourseQueryHandler(CourseRepository courses) {
        this.courses = courses;
    }

    public CourseDto handle(GetCourseQuery q) {
        return LearningDtoAssembler.toDto(courses.findById(q.id()).orElseThrow(() -> new IllegalArgumentException("Course not found")));
    }
}
