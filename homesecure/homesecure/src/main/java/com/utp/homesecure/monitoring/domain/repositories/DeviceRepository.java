package com.utp.homesecure.monitoring.domain.repositories;
import com.utp.homesecure.monitoring.domain.aggregates.Device;
import com.utp.homesecure.monitoring.domain.enums.DeviceType;
import java.util.*;
public interface DeviceRepository {
    Device save(Device device);
    Device update(Device device);
    Optional<Device> findById(Long id);
    Optional<Device> findByCode(String code);
    List<Device> findAll();
    List<Device> findByOwnerAccountId(String ownerAccountId);
    List<Device> findByType(DeviceType type);
    boolean existsById(Long id);
    boolean existsByCode(String code); }
