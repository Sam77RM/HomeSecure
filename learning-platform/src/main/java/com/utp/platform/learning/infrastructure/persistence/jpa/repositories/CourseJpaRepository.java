package com.utp.platform.learning.infrastructure.persistence.jpa.repositories;

import com.utp.platform.learning.infrastructure.persistence.jpa.entities.CourseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseJpaRepository extends JpaRepository<CourseJpaEntity, Long> {
    Optional<CourseJpaEntity> findByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);
}
