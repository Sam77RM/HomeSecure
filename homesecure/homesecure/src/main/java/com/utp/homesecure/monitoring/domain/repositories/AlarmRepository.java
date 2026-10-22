package com.utp.homesecure.monitoring.domain.repositories;

import com.utp.homesecure.monitoring.domain.aggregates.Alarm;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository {
    Alarm save(Alarm alarm);
    Alarm update(Alarm alarm);
    Optional<Alarm> findById(Long id);
    List<Alarm> findAll();
    boolean existsById(Long id);
}
