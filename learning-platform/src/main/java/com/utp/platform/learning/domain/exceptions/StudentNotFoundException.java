package com.utp.platform.learning.domain.exceptions;

import com.utp.platform.learning.domain.valueobjects.AcmeStudentRecordId;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(AcmeStudentRecordId studentRecordId) {
        super("Student with ACME record id %s not found".formatted(studentRecordId.studentRecordId()));
    }
}
