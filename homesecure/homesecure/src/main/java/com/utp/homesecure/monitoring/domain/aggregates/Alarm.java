package com.utp.homesecure.monitoring.domain.aggregates;

import com.utp.homesecure.monitoring.domain.events.AlarmStatusChangedEvent;
import com.utp.homesecure.monitoring.domain.events.AlarmTriggeredEvent;
import com.utp.homesecure.monitoring.domain.enums.AlarmStatus;
import com.utp.homesecure.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.Instant;

public class Alarm extends AbstractDomainAggregateRoot<Alarm> {
    private Long id;
    private String name;
    private AlarmStatus status;
    private Instant lastChangedAt;
    private Long lastChangedByUserId;
    private Long triggeredByIncidentId;
    private final Instant createdAt;

    public Alarm(String name) {
        this(null, name, AlarmStatus.DISARMED, Instant.now(), null, null, Instant.now());
    }

    public Alarm(Long id, String name, AlarmStatus status, Instant lastChangedAt,
                 Long lastChangedByUserId, Long triggeredByIncidentId, Instant createdAt) {
        this.id = id;
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Alarm name is required");
        if (status == null) throw new IllegalArgumentException("Alarm status is required");
        if (lastChangedAt == null) throw new IllegalArgumentException("Last changed at is required");
        if (createdAt == null) throw new IllegalArgumentException("Created at is required");
        this.name = name.trim();
        this.status = status;
        this.lastChangedAt = lastChangedAt;
        this.lastChangedByUserId = lastChangedByUserId;
        this.triggeredByIncidentId = triggeredByIncidentId;
        this.createdAt = createdAt;
    }

    public void rename(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Alarm name is required");
        this.name = name.trim();
    }

    /** Activates the security system (CU06). */
    public void arm(Long userId) {
        if (status != AlarmStatus.DISARMED)
            throw new IllegalStateException("Only a disarmed alarm can be armed");
        changeStatus(AlarmStatus.ARMED, userId);
    }

    /** Deactivates the security system and silences the siren if it was sounding. */
    public void disarm(Long userId) {
        if (status == AlarmStatus.DISARMED)
            throw new IllegalStateException("Alarm is already disarmed");
        triggeredByIncidentId = null;
        changeStatus(AlarmStatus.DISARMED, userId);
    }

    /** Automatic activation after a confirmed intrusion (CU07). */
    public void trigger(Long incidentId) {
        if (incidentId == null || incidentId < 1)
            throw new IllegalArgumentException("Incident id must be positive");
        if (status != AlarmStatus.ARMED)
            throw new IllegalStateException("Only an armed alarm can be triggered automatically");
        triggeredByIncidentId = incidentId;
        changeStatus(AlarmStatus.TRIGGERED, null);
        registerDomainEvent(new AlarmTriggeredEvent(id, incidentId, null, lastChangedAt));
    }

    /** Manual activation of the siren from the web interface (CU06). */
    public void triggerManually(Long userId) {
        if (userId == null || userId < 1) throw new IllegalArgumentException("User id must be positive");
        if (status == AlarmStatus.TRIGGERED)
            throw new IllegalStateException("Alarm is already triggered");
        triggeredByIncidentId = null;
        changeStatus(AlarmStatus.TRIGGERED, userId);
        registerDomainEvent(new AlarmTriggeredEvent(id, null, userId, lastChangedAt));
    }

    public boolean isArmed() {
        return status == AlarmStatus.ARMED;
    }

    public boolean isSounding() {
        return status == AlarmStatus.TRIGGERED;
    }

    private void changeStatus(AlarmStatus newStatus, Long userId) {
        status = newStatus;
        lastChangedAt = Instant.now();
        lastChangedByUserId = userId;
        registerDomainEvent(new AlarmStatusChangedEvent(id, newStatus, userId, lastChangedAt));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AlarmStatus getStatus() {
        return status;
    }

    public Instant getLastChangedAt() {
        return lastChangedAt;
    }

    public Long getLastChangedByUserId() {
        return lastChangedByUserId;
    }

    public Long getTriggeredByIncidentId() {
        return triggeredByIncidentId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
