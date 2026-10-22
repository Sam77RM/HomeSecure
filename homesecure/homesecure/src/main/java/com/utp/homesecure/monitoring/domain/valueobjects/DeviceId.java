package com.utp.homesecure.monitoring.domain.valueobjects;


public record DeviceId(Long deviceId) {

    public DeviceId {
        if (deviceId == null || deviceId < 1) {
            throw new IllegalArgumentException
                    ("Device id cannot be null or less than 1");
        }
    }

}
