package com.utp.homesecure.monitoring.domain.repositories;
import com.utp.homesecure.monitoring.domain.aggregates.Incident;
import com.utp.homesecure.monitoring.domain.enums.IncidentStatus;
import com.utp.homesecure.monitoring.domain.enums.IncidentType;
import com.utp.homesecure.monitoring.domain.enums.Priority;
import java.time.Instant;
import java.util.*;
public interface IncidentRepository {
    Incident save(Incident incident);
    Incident update(Incident incident);
    Optional<Incident> findById(Long id);
    List<Incident> findByDeviceId(Long deviceId);
    List<Incident> findByStatus(IncidentStatus status);
    /** Filterable history (RF08). Any argument may be null to skip that filter. */
    List<Incident> search(Long deviceId, IncidentType type, Priority priority,
                          IncidentStatus status, Instant from, Instant to);
    boolean existsOpenIncident(Long deviceId, IncidentType type);
    long countByDeviceIdAndStatus(Long deviceId, IncidentStatus status);
}
