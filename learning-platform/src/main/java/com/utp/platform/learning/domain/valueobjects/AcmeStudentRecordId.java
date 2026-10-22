package com.utp.platform.learning.domain.valueobjects;


import java.util.UUID;

public record AcmeStudentRecordId(String studentRecordId) {

    public AcmeStudentRecordId() {
        this(UUID.randomUUID().toString());
    }
    public AcmeStudentRecordId{
        if(studentRecordId==null || studentRecordId.isBlank()){
            throw new IllegalArgumentException("Student record id" +
                    "cannot be null or empty");
        }
        try{
            UUID.fromString(studentRecordId);
        }catch (IllegalArgumentException exception){
            throw new IllegalArgumentException("Student " +
                    "record id must be a valid UUID");
        }



    }
}
