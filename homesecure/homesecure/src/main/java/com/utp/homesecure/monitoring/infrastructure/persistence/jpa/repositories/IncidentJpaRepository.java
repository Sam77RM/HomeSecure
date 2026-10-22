package com.utp.homesecure.monitoring.infrastructure.persistence.jpa.repositories;

import com.utp.homesecure.monitoring.domain.enums.IncidentStatus;
import com.utp.homesecure.monitoring.domain.enums.IncidentType;
import com.utp.homesecure.monitoring.domain.enums.Priority;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities.IncidentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public interface IncidentJpaRepository extends JpaRepository<IncidentJpaEntity, Long> {
    List<IncidentJpaEntity> findByDevice_Id(Long deviceId);

    List<IncidentJpaEntity> findByStatus(IncidentStatus status);

    boolean existsByDevice_IdAndTypeAndStatusIn(Long deviceId, IncidentType type, Collection<IncidentStatus> statuses);

    long countByDevice_IdAndStatus(Long deviceId, IncidentStatus status);

    @Query("""
            SELECT i FROM IncidentJpaEntity i
            WHERE (:deviceId IS NULL OR i.device.id = :deviceId)
              AND (:type IS NULL OR i.type = :type)
              AND (:priority IS NULL OR i.priority = :priority)
              AND (:status IS NULL OR i.status = :status)
              AND (:from IS NULL OR i.occurredAt >= :from)
              AND (:to IS NULL OR i.occurredAt <= :to)
            ORDER BY i.occurredAt DESC
            """)
    List<IncidentJpaEntity> search(@Param("deviceId") Long deviceId,
                                   @Param("type") IncidentType type,
                                   @Param("priority") Priority priority,
                                   @Param("status") IncidentStatus status,
                                   @Param("from") Instant from,
                                   @Param("to") Instant to);
}
