package com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities;

import com.utp.homesecure.monitoring.domain.enums.DeviceStatus;
import com.utp.homesecure.monitoring.domain.enums.DeviceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "devices", uniqueConstraints = @UniqueConstraint(name = "uk_device_code", columnNames = "code"))
public class DeviceJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "owner_account_id", nullable = false, length = 36)
    private String ownerAccountId;
    @Column(nullable = false, length = 30)
    private String code;
    @Column(nullable = false, length = 150)
    private String name;
    @Column(length = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DeviceType type;
    @Column(length = 60)
    private String zone;
    @Column(name = "stream_url", length = 500)
    private String streamUrl;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DeviceStatus status;
    @Column(nullable = false)
    private boolean enabled;
    @Column(name = "last_communication_at")
    private Instant lastCommunicationAt;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
