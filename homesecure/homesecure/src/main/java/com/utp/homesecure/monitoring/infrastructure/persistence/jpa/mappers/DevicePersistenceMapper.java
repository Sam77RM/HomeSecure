package com.utp.homesecure.monitoring.infrastructure.persistence.jpa.mappers;

import com.utp.homesecure.monitoring.domain.aggregates.Device;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities.DeviceJpaEntity;
import com.utp.homesecure.monitoring.domain.valueobjects.OwnerAccountId;
import org.springframework.stereotype.Component;

@Component
public class DevicePersistenceMapper {
    public Device toDomain(DeviceJpaEntity e) {
        return new Device(e.getId(), new OwnerAccountId(e.getOwnerAccountId()), e.getCode(),
                e.getName(), e.getDescription(),
                e.getType(),
                e.getZone(),
                e.getStreamUrl(),
                e.getStatus(),
                e.isEnabled(),
                e.getLastCommunicationAt(),
                e.getCreatedAt());
    }

    public DeviceJpaEntity toEntity(Device d) {
        DeviceJpaEntity e = new DeviceJpaEntity();
        e.setId(d.getId());
        e.setOwnerAccountId(d.getOwnerAccountId().value());
        e.setCode(d.getCode());
        e.setName(d.getName());
        e.setDescription(d.getDescription());
        e.setType(d.getType());
        e.setZone(d.getZone());
        e.setStreamUrl(d.getStreamUrl());
        e.setStatus(d.getStatus());
        e.setEnabled(d.isEnabled());
        e.setLastCommunicationAt(d.getLastCommunicationAt());
        e.setCreatedAt(d.getCreatedAt());
        return e;
    }
}
