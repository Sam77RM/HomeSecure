package com.utp.homesecure.monitoring.domain.exceptions;

public class AlarmNotFoundException extends
RuntimeException{
    public AlarmNotFoundException(Long alarmId){
        super(String.format("Alarm with ID %s not found",
                alarmId));
    }

}
