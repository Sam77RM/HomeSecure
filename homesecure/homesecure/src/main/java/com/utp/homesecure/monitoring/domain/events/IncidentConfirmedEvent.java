package com.utp.homesecure.monitoring.domain.events;

import com.utp.homesecure.monitoring.domain.enums.IncidentType;

import java.time.Instant;

public record IncidentConfirmedEvent(Long incidentId,
                                     Long deviceId, IncidentType type, Instant occurredAt) {
}
