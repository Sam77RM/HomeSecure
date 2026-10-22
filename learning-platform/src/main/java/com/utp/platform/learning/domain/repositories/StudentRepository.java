package com.utp.platform.learning.domain.repositories;

import com.utp.platform.learning.domain.aggregates.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);
    Student update(Student student);
    Optional<Student> findById(Long id);
    List<Student> findAll();
    boolean existsById(Long id);
    Optional<Student> findByRecordId(String recordId);
}
