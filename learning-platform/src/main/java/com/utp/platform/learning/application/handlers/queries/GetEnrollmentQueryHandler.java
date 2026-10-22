package com.utp.platform.learning.application.handlers.queries;

import com.utp.platform.learning.application.assemblers.LearningDtoAssembler;
import com.utp.platform.learning.application.dtos.EnrollmentDto;
import com.utp.platform.learning.application.queries.GetEnrollmentQuery;
import com.utp.platform.learning.domain.repositories.EnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetEnrollmentQueryHandler {
    private final EnrollmentRepository enrollments;

    public GetEnrollmentQueryHandler(EnrollmentRepository enrollments) {
        this.enrollments = enrollments;
    }

    public EnrollmentDto handle(GetEnrollmentQuery q) {
        return LearningDtoAssembler.toDto(enrollments.findById(q.id()).orElseThrow(() -> new IllegalArgumentException("Enrollment not found")));
    }
}
