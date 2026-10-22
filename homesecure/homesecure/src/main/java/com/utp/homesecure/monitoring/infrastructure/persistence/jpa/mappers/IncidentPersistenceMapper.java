package com.utp.homesecure.monitoring.infrastructure.persistence.jpa.mappers;

import com.utp.homesecure.monitoring.domain.aggregates.Incident;
import com.utp.homesecure.monitoring.domain.entities.NotificationItem;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities.DeviceJpaEntity;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities.IncidentJpaEntity;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities.NotificationItemJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class IncidentPersistenceMapper {

    public Incident toDomain(IncidentJpaEntity e) {
        return new Incident(
                e.getId(),
                e.getDevice().getId(),
                e.getType(),
                e.getPriority(),
                e.getDescription(),
                e.getStatus(),
                e.getOccurredAt(),
                e.getConfirmedAt(),
                e.getResolvedAt(),
                e.getNotifications().stream().map(this::toDomainItem).toList()
        );
    }

    public IncidentJpaEntity toEntity(
            Incident d,
            DeviceJpaEntity device) {

        IncidentJpaEntity e = new IncidentJpaEntity();
        e.setId(d.getId());
        e.setDevice(device);
        e.setType(d.getType());
        e.setPriority(d.getPriority());
        e.setDescription(d.getDescription());
        e.setStatus(d.getStatus());
        e.setOccurredAt(d.getOccurredAt());
        e.setConfirmedAt(d.getConfirmedAt());
        e.setResolvedAt(d.getResolvedAt());
        e.replaceNotifications(
                d.getNotifications().stream().map(this::toEntityItem).toList()
        );
        return e;
    }

    private NotificationItem toDomainItem(NotificationItemJpaEntity e) {
        return new NotificationItem(
                e.getId(),
                e.getChannel(),
                e.getStatus(),
                e.getAttempts(),
                e.getSentAt()
        );
    }

    private NotificationItemJpaEntity toEntityItem(NotificationItem d) {
        NotificationItemJpaEntity e = new NotificationItemJpaEntity();
        e.setId(d.getId());
        e.setChannel(d.getChannel());
        e.setStatus(d.getStatus());
        e.setAttempts(d.getAttempts());
        e.setSentAt(d.getSentAt());
        return e;
    }
}
