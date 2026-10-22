package com.utp.homesecure.monitoring.infrastructure.persistence.jpa.mappers;

import com.utp.homesecure.monitoring.domain.aggregates.Alarm;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities.AlarmJpaEntity;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities.IncidentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class AlarmPersistenceMapper {
    public Alarm toDomain(AlarmJpaEntity e) {
        return new Alarm(
                e.getId(),
                e.getName(),
                e.getStatus(),
                e.getLastChangedAt(),
                e.getLastChangedByUserId(),
                e.getTriggeredByIncident() == null ? null : e.getTriggeredByIncident().getId(),
                e.getCreatedAt());
    }

    public AlarmJpaEntity toEntity(
            Alarm a,
            IncidentJpaEntity triggeredByIncident) {

        AlarmJpaEntity e = new AlarmJpaEntity();
        e.setId(a.getId());
        e.setName(a.getName());
        e.setStatus(a.getStatus());
        e.setLastChangedAt(a.getLastChangedAt());
        e.setLastChangedByUserId(a.getLastChangedByUserId());
        e.setTriggeredByIncident(triggeredByIncident);
        e.setCreatedAt(a.getCreatedAt());
        return e;
    }
}
