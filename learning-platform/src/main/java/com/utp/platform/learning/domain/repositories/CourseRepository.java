package com.utp.platform.learning.domain.repositories;
import com.utp.platform.learning.domain.aggregates.Course;
import java.util.*;
public interface CourseRepository {
    Course save(Course course);
    Course update(Course course);
    Optional<Course> findById(Long id);
    Optional<Course> findByCode(String code);
    List<Course> findAll();
    boolean existsById(Long id);
    boolean existsByCode(String code); }
