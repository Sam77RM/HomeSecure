package com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities;

import com.utp.homesecure.monitoring.domain.enums.IncidentStatus;
import com.utp.homesecure.monitoring.domain.enums.IncidentType;
import com.utp.homesecure.monitoring.domain.enums.Priority;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "incidents", indexes = {@Index(name = "ix_incident_device", columnList = "device_id"), @Index(name = "ix_incident_status", columnList = "status"), @Index(name = "ix_incident_occurred_at", columnList = "occurred_at")})
public class IncidentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceJpaEntity device;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private IncidentType type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Priority priority;
    @Column(length = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IncidentStatus status;
    @Column(name = "occurred_at", nullable = false, updatable = false)
    private Instant occurredAt;
    @Column(name = "confirmed_at")
    private Instant confirmedAt;
    @Column(name = "resolved_at")
    private Instant resolvedAt;
    @OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<NotificationItemJpaEntity> notifications = new ArrayList<>();

    public void replaceNotifications(List<NotificationItemJpaEntity> items) {
        notifications.clear();
        if (items != null) {
            items.forEach(i -> i.setIncident(this));
            notifications.addAll(items);
        }
    }
}
