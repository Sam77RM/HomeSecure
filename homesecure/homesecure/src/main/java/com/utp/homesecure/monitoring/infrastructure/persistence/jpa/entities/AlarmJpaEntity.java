package com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities;

import com.utp.homesecure.monitoring.domain.enums.AlarmStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "alarms")
public class AlarmJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AlarmStatus status;
    @Column(name = "last_changed_at", nullable = false)
    private Instant lastChangedAt;
    /** Id of the user in the IAM/User module (plain reference, no JPA relation across modules). Null when changed by the system. */
    @Column(name = "last_changed_by_user_id")
    private Long lastChangedByUserId;
    /** Null when the alarm is disarmed or was triggered manually. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "triggered_by_incident_id")
    private IncidentJpaEntity triggeredByIncident;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
