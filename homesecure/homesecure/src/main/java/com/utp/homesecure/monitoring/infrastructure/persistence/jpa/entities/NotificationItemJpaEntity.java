package com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities;

import com.utp.homesecure.monitoring.domain.enums.NotificationChannel;
import com.utp.homesecure.monitoring.domain.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "incident_notifications", uniqueConstraints = @UniqueConstraint(name = "uk_incident_channel", columnNames = {"incident_id", "channel"}))
public class NotificationItemJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "incident_id", nullable = false)
    private IncidentJpaEntity incident;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationChannel channel;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationStatus status;
    @Column(nullable = false)
    private int attempts;
    @Column(name = "sent_at")
    private Instant sentAt;
}
