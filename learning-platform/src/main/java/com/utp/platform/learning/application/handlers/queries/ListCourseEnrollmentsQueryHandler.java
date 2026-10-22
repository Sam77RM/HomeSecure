package com.utp.platform.learning.application.handlers.queries;

import com.utp.platform.learning.application.assemblers.LearningDtoAssembler;
import com.utp.platform.learning.application.dtos.EnrollmentDto;
import com.utp.platform.learning.application.queries.ListCourseEnrollmentsQuery;
import com.utp.platform.learning.domain.repositories.EnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ListCourseEnrollmentsQueryHandler {
    private final EnrollmentRepository enrollments;

    public ListCourseEnrollmentsQueryHandler(EnrollmentRepository enrollments) {
        this.enrollments = enrollments;
    }

    public List<EnrollmentDto> handle(ListCourseEnrollmentsQuery q) {
        return enrollments.findByCourseId(q.courseId()).stream().map(LearningDtoAssembler::toDto).toList();
    }
}
