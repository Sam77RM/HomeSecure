package com.utp.homesecure.monitoring.domain.exceptions;


public class IncidentRegistrationException
extends RuntimeException{
    public
    IncidentRegistrationException(String exceptionMessage){
        super("Error while registering the incident: %s".formatted(exceptionMessage));
    }
}
