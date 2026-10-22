package com.utp.platform.learning.infrastructure.persistence.jpa.repositories;

import com.utp.platform.learning.infrastructure.persistence.jpa.entities.StudentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentJpaRepository extends JpaRepository<StudentJpaEntity, Long> {
    boolean existsByEmailIgnoreCase(String email);

    Optional<StudentJpaEntity> findByRecordId(String recordId);
}
