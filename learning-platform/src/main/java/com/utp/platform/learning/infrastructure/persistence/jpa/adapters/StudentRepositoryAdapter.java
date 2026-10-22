package com.utp.platform.learning.infrastructure.persistence.jpa.adapters;

import com.utp.platform.learning.domain.aggregates.Student;
import com.utp.platform.learning.domain.repositories.StudentRepository;
import com.utp.platform.learning.infrastructure.persistence.jpa.mappers.StudentPersistenceMapper;
import com.utp.platform.learning.infrastructure.persistence.jpa.repositories.StudentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepositoryAdapter implements StudentRepository {
    private final StudentJpaRepository repository;
    private final StudentPersistenceMapper mapper;

    public StudentRepositoryAdapter(StudentJpaRepository repository, StudentPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Student save(Student student) {

        return mapper.toDomain(repository.save(mapper.toEntity(student)));
    }

    @Override
    public Student update(Student student) {

        return mapper.toDomain(repository.save(mapper.toEntity(student)));
    }

    @Override
    public Optional<Student> findById(Long id) {

        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Student> findAll() {

        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsById(Long id) {

        return repository.existsById(id);
    }

    @Override
    public Optional<Student> findByRecordId(String recordId) {
        return repository.findByRecordId(recordId).map(mapper::toDomain);
    }
}
