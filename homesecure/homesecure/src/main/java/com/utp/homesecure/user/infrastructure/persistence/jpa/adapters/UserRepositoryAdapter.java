package com.utp.homesecure.user.infrastructure.persistence.jpa.adapters;

import com.utp.homesecure.user.domain.aggregates.User;
import com.utp.homesecure.user.domain.repositories.UserRepository;
import com.utp.homesecure.user.infrastructure.persistence.jpa.mappers.UserPersistenceMapper;
import com.utp.homesecure.user.infrastructure.persistence.jpa.repositories.UserJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository repository;
    private final UserPersistenceMapper mapper;

    public UserRepositoryAdapter(UserJpaRepository repository, UserPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override public User save(User user) { return mapper.toDomain(repository.save(mapper.toEntity(user))); }
    @Override public User update(User user) { return save(user); }
    @Override public Optional<User> findById(Long id) { return repository.findById(id).map(mapper::toDomain); }
    @Override public List<User> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    @Override public boolean existsById(Long id) { return repository.existsById(id); }
    @Override public Optional<User> findByAccountId(String accountId) { return repository.findByAccountId(accountId).map(mapper::toDomain); }
    @Override public Optional<User> findByEmail(String email) { return repository.findByEmailIgnoreCase(email).map(mapper::toDomain); }
    @Override public boolean existsByEmail(String email) { return repository.existsByEmailIgnoreCase(email); }
}
