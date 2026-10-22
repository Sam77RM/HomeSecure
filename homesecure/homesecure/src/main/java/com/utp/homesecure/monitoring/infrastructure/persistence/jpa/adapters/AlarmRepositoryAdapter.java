package com.utp.homesecure.monitoring.infrastructure.persistence.jpa.adapters;

import com.utp.homesecure.monitoring.domain.aggregates.Alarm;
import com.utp.homesecure.monitoring.domain.repositories.AlarmRepository;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities.AlarmJpaEntity;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.entities.IncidentJpaEntity;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.mappers.AlarmPersistenceMapper;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.repositories.AlarmJpaRepository;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.repositories.IncidentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AlarmRepositoryAdapter implements AlarmRepository {
    private final AlarmJpaRepository repo;
    private final AlarmPersistenceMapper mapper;
    private final IncidentJpaRepository incidentRepo;

    public AlarmRepositoryAdapter(
            AlarmJpaRepository repo,
            AlarmPersistenceMapper mapper,
            IncidentJpaRepository incidentRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.incidentRepo = incidentRepo;
    }

    @Override
    public Alarm save(Alarm alarm) {
        IncidentJpaEntity incident = alarm.getTriggeredByIncidentId() == null ? null
                : incidentRepo.findById(alarm.getTriggeredByIncidentId())
                .orElseThrow(() -> new IllegalArgumentException("Incident not found: " + alarm.getTriggeredByIncidentId()));
        AlarmJpaEntity entity = mapper.toEntity(alarm, incident);
        return mapper.toDomain(repo.save(entity));
    }

    @Override
    public Alarm update(Alarm alarm) {
        return save(alarm);
    }

    @Override
    public Optional<Alarm> findById(Long id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Alarm> findAll() {
        return repo.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }
}
