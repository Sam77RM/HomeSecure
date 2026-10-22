package com.utp.platform.learning.domain.exceptions;

public class CourseNotFoundException extends
RuntimeException{
    public  CourseNotFoundException(Long courseId){

        super(String.format("Course with ID %s not found",courseId));
    }
}
