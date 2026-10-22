package com.utp.homesecure.monitoring.domain.events;

import com.utp.homesecure.monitoring.domain.enums.AlarmStatus;

import java.time.Instant;

public record AlarmStatusChangedEvent(Long alarmId, AlarmStatus status,
                                      Long changedByUserId, Instant occurredAt) {}
