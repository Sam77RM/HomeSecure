package com.utp.homesecure.monitoring.domain.exceptions;

public class IncidentNotFoundException extends
RuntimeException{
    public IncidentNotFoundException(Long incidentId){
        super(String.format("Incident with ID %s not found",
                incidentId));
    }

}
