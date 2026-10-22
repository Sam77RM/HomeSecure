package com.utp.platform.learning.domain.exceptions;


public class EnrollmentRequestException
extends RuntimeException{
    public
    EnrollmentRequestException(String exceptionMessage){
        super("Error while creating the enrollment: %s".formatted(exceptionMessage));
    }
}
