package com.utp.homesecure.user.infrastructure.persistence.jpa.repositories;

import com.utp.homesecure.user.infrastructure.persistence.jpa.entities.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    boolean existsByEmailIgnoreCase(String email);
    Optional<UserJpaEntity> findByEmailIgnoreCase(String email);
    Optional<UserJpaEntity> findByAccountId(String accountId);
}
