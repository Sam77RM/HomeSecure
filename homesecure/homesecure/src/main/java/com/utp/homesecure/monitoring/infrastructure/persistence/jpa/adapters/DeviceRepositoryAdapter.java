package com.utp.homesecure.monitoring.infrastructure.persistence.jpa.adapters;

import com.utp.homesecure.monitoring.domain.aggregates.Device;
import com.utp.homesecure.monitoring.domain.repositories.DeviceRepository;
import com.utp.homesecure.monitoring.domain.enums.DeviceType;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.mappers.DevicePersistenceMapper;
import com.utp.homesecure.monitoring.infrastructure.persistence.jpa.repositories.DeviceJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DeviceRepositoryAdapter implements DeviceRepository {
    private final DeviceJpaRepository repo;
    private final DevicePersistenceMapper mapper;

    public DeviceRepositoryAdapter(DeviceJpaRepository r, DevicePersistenceMapper m) {
        repo = r;
        mapper = m;
    }

    public Device save(Device d) {
        return mapper.toDomain(repo.save(mapper.toEntity(d)));
    }

    public Device update(Device d) {
        return save(d);
    }

    public Optional<Device> findById(Long id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    public Optional<Device> findByCode(String code) {
        return repo.findByCodeIgnoreCase(code).map(mapper::toDomain);
    }

    public List<Device> findByOwnerAccountId(String ownerAccountId) {
        return repo.findByOwnerAccountId(ownerAccountId).stream().map(mapper::toDomain).toList();
    }

    public List<Device> findAll() {
        return repo.findAll().stream().map(mapper::toDomain).toList();
    }

    public List<Device> findByType(DeviceType type) {
        return repo.findByType(type).stream().map(mapper::toDomain).toList();
    }

    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    public boolean existsByCode(String code) {
        return repo.existsByCodeIgnoreCase(code);
    }
}
