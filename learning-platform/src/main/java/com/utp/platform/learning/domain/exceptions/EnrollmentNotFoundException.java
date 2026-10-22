package com.utp.platform.learning.domain.exceptions;

public class EnrollmentNotFoundException extends
RuntimeException{
    public EnrollmentNotFoundException(Long enrollmentId){
        super(String.format("Enrollment with ID %s not found",
                enrollmentId));
    }

}
