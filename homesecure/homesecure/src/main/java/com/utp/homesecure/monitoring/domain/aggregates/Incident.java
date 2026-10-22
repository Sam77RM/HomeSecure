package com.utp.homesecure.monitoring.domain.aggregates;

import com.utp.homesecure.monitoring.domain.entities.NotificationItem;
import com.utp.homesecure.monitoring.domain.events.IncidentConfirmedEvent;
import com.utp.homesecure.monitoring.domain.events.IncidentDetectedEvent;
import com.utp.homesecure.monitoring.domain.enums.IncidentStatus;
import com.utp.homesecure.monitoring.domain.enums.IncidentType;
import com.utp.homesecure.monitoring.domain.enums.NotificationChannel;
import com.utp.homesecure.monitoring.domain.enums.Priority;
import com.utp.homesecure.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.Instant;
import java.util.*;

public class Incident extends AbstractDomainAggregateRoot<Incident> {
    private Long id;
    private final Long deviceId;
    private final IncidentType type;
    private Priority priority;
    private final String description;
    private IncidentStatus status;
    private final Instant occurredAt;
    private Instant confirmedAt;
    private Instant resolvedAt;
    private final List<NotificationItem> notifications;

    public Incident(Long deviceId, IncidentType type, Priority priority, String description) {
        this(null, deviceId, type, priority, description, IncidentStatus.DETECTED, Instant.now(), null, null, List.of());
        registerDomainEvent(new IncidentDetectedEvent(id, deviceId, type, priority, occurredAt));
    }

    public Incident(Long id, Long deviceId, IncidentType type, Priority priority, String description,
                    IncidentStatus status, Instant occurredAt, Instant confirmedAt, Instant resolvedAt,
                    List<NotificationItem> notifications) {
        this.id = id;
        this.deviceId = positive(deviceId, "Device id");
        if (type == null) throw new IllegalArgumentException("Incident type is required");
        if (priority == null) throw new IllegalArgumentException("Priority is required");
        if (status == null) throw new IllegalArgumentException("Incident status is required");
        if (occurredAt == null) throw new IllegalArgumentException("Occurred at is required");
        this.type = type;
        this.priority = priority;
        this.description = description == null ? "" : description.trim();
        this.status = status;
        this.occurredAt = occurredAt;
        this.confirmedAt = confirmedAt;
        this.resolvedAt = resolvedAt;
        this.notifications = new ArrayList<>(notifications == null ? List.of() : notifications);
    }

    /** The server rules (or the owner) confirm that the incident is a real intrusion. */
    public void confirm() {
        if (status != IncidentStatus.DETECTED)
            throw new IllegalStateException("Only a detected incident can be confirmed");
        status = IncidentStatus.CONFIRMED;
        confirmedAt = Instant.now();
        registerDomainEvent(new IncidentConfirmedEvent(id, deviceId, type, confirmedAt));
    }

    /** Discards the incident as a false alarm. */
    public void dismiss() {
        if (status != IncidentStatus.DETECTED)
            throw new IllegalStateException("Only a detected incident can be dismissed");
        status = IncidentStatus.DISMISSED;
        resolvedAt = Instant.now();
    }

    public void resolve() {
        if (status != IncidentStatus.CONFIRMED)
            throw new IllegalStateException("Only a confirmed incident can be resolved");
        status = IncidentStatus.RESOLVED;
        resolvedAt = Instant.now();
    }

    public void changePriority(Priority priority) {
        if (priority == null) throw new IllegalArgumentException("Priority is required");
        if (status == IncidentStatus.DISMISSED || status == IncidentStatus.RESOLVED)
            throw new IllegalStateException("Priority cannot be changed on a closed incident");
        this.priority = priority;
    }

    public void requestNotifications(Collection<NotificationChannel> channels) {
        if (status != IncidentStatus.CONFIRMED)
            throw new IllegalStateException("Notifications can only be requested for a confirmed incident");
        if (!notifications.isEmpty()) throw new IllegalStateException("Notifications have already been requested");
        if (channels != null)
            channels.stream().filter(Objects::nonNull).distinct().forEach(c -> notifications.add(new NotificationItem(c)));
    }

    public void markNotificationSent(NotificationChannel channel) {
        notificationItem(channel).markSent();
    }

    public void markNotificationFailed(NotificationChannel channel) {
        notificationItem(channel).markFailed();
    }

    public void retryNotification(NotificationChannel channel) {
        notificationItem(channel).retry();
    }

    public int deliveryPercentage() {
        if (notifications.isEmpty()) return 0;
        long sent = notifications.stream().filter(NotificationItem::isSent).count();
        return (int) ((sent * 100) / notifications.size());
    }

    public boolean isOpen() {
        return status == IncidentStatus.DETECTED || status == IncidentStatus.CONFIRMED;
    }

    private NotificationItem notificationItem(NotificationChannel channel) {
        return notifications.stream().filter(i -> i.getChannel().equals(channel)).findFirst().orElseThrow(() -> new IllegalArgumentException("Channel is not part of this incident"));
    }

    private static Long positive(Long value, String name) {
        if (value == null || value < 1) throw new IllegalArgumentException(name + " must be positive");
        return value;
    }

    public Long getId() {
        return id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public IncidentType getType() {
        return type;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public Instant getConfirmedAt() {
        return confirmedAt;
    }

    public Instant getResolvedAt() {
        return resolvedAt;
    }

    public List<NotificationItem> getNotifications() {
        return Collections.unmodifiableList(notifications);
    }
}
