package com.utp.homesecure.monitoring.domain.events;

import com.utp.homesecure.monitoring.domain.enums.IncidentType;
import com.utp.homesecure.monitoring.domain.enums.Priority;

import java.time.Instant;

public record IncidentDetectedEvent(Long incidentId, Long deviceId,
                                    IncidentType type, Priority priority, Instant occurredAt) {}
