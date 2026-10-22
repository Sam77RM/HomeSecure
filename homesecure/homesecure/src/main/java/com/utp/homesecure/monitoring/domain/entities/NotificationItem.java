package com.utp.homesecure.monitoring.domain.entities;

import com.utp.homesecure.monitoring.domain.enums.NotificationChannel;
import com.utp.homesecure.monitoring.domain.enums.NotificationStatus;

import java.time.Instant;

public class NotificationItem {
    private Long id;
    private final NotificationChannel channel;
    private NotificationStatus status;
    private int attempts;
    private Instant sentAt;

    public NotificationItem(NotificationChannel channel) {
        this(null, channel, NotificationStatus.PENDING, 0, null);
    }

    public NotificationItem(Long id, NotificationChannel channel, NotificationStatus status, int attempts, Instant sentAt) {
        if (channel == null) throw new IllegalArgumentException("Notification channel is required");
        if (status == null) throw new IllegalArgumentException("Notification status is required");
        if (attempts < 0) throw new IllegalArgumentException("Attempts cannot be negative");
        this.id = id;
        this.channel = channel;
        this.status = status;
        this.attempts = attempts;
        this.sentAt = sentAt;
    }

    public void markSent() {
        if (status != NotificationStatus.PENDING)
            throw new IllegalStateException("Only a pending notification can be marked as sent");
        status = NotificationStatus.SENT;
        attempts++;
        sentAt = Instant.now();
    }

    public void markFailed() {
        if (status != NotificationStatus.PENDING)
            throw new IllegalStateException("Only a pending notification can be marked as failed");
        status = NotificationStatus.FAILED;
        attempts++;
    }

    public void retry() {
        if (status != NotificationStatus.FAILED)
            throw new IllegalStateException("Only a failed notification can be retried");
        status = NotificationStatus.PENDING;
    }

    public boolean isSent() {
        return status == NotificationStatus.SENT;
    }

    public Long getId() {
        return id;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public int getAttempts() {
        return attempts;
    }

    public Instant getSentAt() {
        return sentAt;
    }
}
