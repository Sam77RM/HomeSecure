package com.utp.homesecure.monitoring.domain.exceptions;

public class DeviceNotFoundException extends
RuntimeException{
    public DeviceNotFoundException(Long deviceId){

        super(String.format("Device with ID %s not found",deviceId));
    }
}
