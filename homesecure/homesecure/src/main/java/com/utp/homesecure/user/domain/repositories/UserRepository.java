package com.utp.homesecure.user.domain.repositories;

import com.utp.homesecure.user.domain.aggregates.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    User update(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    boolean existsById(Long id);
    Optional<User> findByAccountId(String accountId);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
