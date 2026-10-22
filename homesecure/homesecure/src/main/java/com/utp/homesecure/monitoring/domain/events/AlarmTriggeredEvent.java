package com.utp.homesecure.monitoring.domain.events;

import java.time.Instant;

/** incidentId is null when the alarm is triggered manually from the web interface. */
public record AlarmTriggeredEvent(Long alarmId, Long incidentId, Long triggeredByUserId, Instant occurredAt) {}
