package com.utp.homesecure.monitoring.infrastructure.persistence.jpa.adapters;

import com.utp.homesecure.monitoring.domain.aggregates.Incident;
import com.utp.homesecure.monitoring.domain.repositories.IncidentRepository;
import com.utp.homesecure.monitoring.domain.enums.IncidentStatus;
import com.utp.homesecure.monitoring.domain.enums.IncidentType;
import com.utp.homesecure.monitoring.domain.enums.Priority;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities.DeviceJpaEntity;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities.IncidentJpaEntity;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.mappers.IncidentPersistenceMapper;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.repositories.DeviceJpaRepository;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.repositories.IncidentJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public class IncidentRepositoryAdapter implements IncidentRepository {
    private final IncidentJpaRepository repo;
    private final IncidentPersistenceMapper mapper;
    private final DeviceJpaRepository deviceRepo;

    public IncidentRepositoryAdapter(
            IncidentJpaRepository repo,
            IncidentPersistenceMapper mapper,
            DeviceJpaRepository deviceRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.deviceRepo = deviceRepo;
    }

    @Override
    public Incident save(Incident incident) {
        DeviceJpaEntity device = deviceRepo.findById(incident.getDeviceId())
                .orElseThrow(() -> new IllegalArgumentException("Device not found: " + incident.getDeviceId()));
        IncidentJpaEntity entity = mapper.toEntity(incident, device);
        return mapper.toDomain(repo.save(entity));
    }

    @Override
    public Incident update(Incident incident) {
        return save(incident);
    }

    @Override
    public Optional<Incident> findById(Long id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Incident> findByDeviceId(Long id) {
        return repo.findByDevice_Id(id).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Incident> findByStatus(IncidentStatus status) {
        return repo.findByStatus(status).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Incident> search(Long deviceId, IncidentType type, Priority priority,
                                 IncidentStatus status, Instant from, Instant to) {
        return repo.search(deviceId, type, priority, status, from, to).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsOpenIncident(Long deviceId, IncidentType type) {
        return repo.existsByDevice_IdAndTypeAndStatusIn(
                deviceId, type, List.of(IncidentStatus.DETECTED, IncidentStatus.CONFIRMED));
    }

    @Override
    public long countByDeviceIdAndStatus(Long deviceId, IncidentStatus status) {
        return repo.countByDevice_IdAndStatus(deviceId, status);
    }
}
