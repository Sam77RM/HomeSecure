package com.utp.homesecure.monitoring.infrastructure.persistence.jpa.repositories;

import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities.AlarmJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmJpaRepository extends JpaRepository<AlarmJpaEntity, Long> {
}
