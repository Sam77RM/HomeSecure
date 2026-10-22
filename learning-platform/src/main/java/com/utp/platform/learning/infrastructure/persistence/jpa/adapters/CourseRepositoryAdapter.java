package com.utp.platform.learning.infrastructure.persistence.jpa.adapters;

import com.utp.platform.learning.domain.aggregates.Course;
import com.utp.platform.learning.domain.repositories.CourseRepository;
import com.utp.platform.learning.infrastructure.persistence.jpa.mappers.CoursePersistenceMapper;
import com.utp.platform.learning.infrastructure.persistence.jpa.repositories.CourseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CourseRepositoryAdapter implements CourseRepository {
    private final CourseJpaRepository repo;
    private final CoursePersistenceMapper mapper;

    public CourseRepositoryAdapter(CourseJpaRepository r, CoursePersistenceMapper m) {
        repo = r;
        mapper = m;
    }

    public Course save(Course c) {
        return mapper.toDomain(repo.save(mapper.toEntity(c)));
    }

    public Course update(Course c) {
        return save(c);
    }

    public Optional<Course> findById(Long id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    public Optional<Course> findByCode(String code) {
        return repo.findByCodeIgnoreCase(code).map(mapper::toDomain);
    }

    public List<Course> findAll() {
        return repo.findAll().stream().map(mapper::toDomain).toList();
    }

    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    public boolean existsByCode(String code) {
        return repo.existsByCodeIgnoreCase(code);
    }
}
