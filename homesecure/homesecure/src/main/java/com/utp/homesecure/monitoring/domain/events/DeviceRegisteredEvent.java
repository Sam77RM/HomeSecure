package com.utp.homesecure.monitoring.domain.events;

import com.utp.homesecure.monitoring.domain.enums.DeviceType;

import java.time.Instant;

public record DeviceRegisteredEvent(Long deviceId, String code, DeviceType type, Instant occurredAt) {}
