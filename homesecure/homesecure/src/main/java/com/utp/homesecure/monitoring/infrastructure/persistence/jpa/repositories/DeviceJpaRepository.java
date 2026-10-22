package com.utp.homesecure.monitoring.infrastructure.persistence.jpa.repositories;

import com.utp.homesecure.monitoring.domain.enums.DeviceType;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities.DeviceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceJpaRepository extends JpaRepository<DeviceJpaEntity, Long> {
    Optional<DeviceJpaEntity> findByCodeIgnoreCase(String code);

    List<DeviceJpaEntity> findByOwnerAccountId(String ownerAccountId);

    boolean existsByCodeIgnoreCase(String code);

    List<DeviceJpaEntity> findByType(DeviceType type);
}
