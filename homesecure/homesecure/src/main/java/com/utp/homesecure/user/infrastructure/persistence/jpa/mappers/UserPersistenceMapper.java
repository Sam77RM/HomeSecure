package com.utp.homesecure.user.infrastructure.persistence.jpa.mappers;

import com.utp.homesecure.user.domain.aggregates.User;
import com.utp.homesecure.user.domain.valueobjects.Email;
import com.utp.homesecure.user.domain.valueobjects.PasswordHash;
import com.utp.homesecure.user.domain.valueobjects.UserAccountId;
import com.utp.homesecure.user.infrastructure.persistence.jpa.entities.UserJpaEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserPersistenceMapper {
    public User toDomain(UserJpaEntity entity) {
        return new User(entity.getId(), new UserAccountId(entity.getAccountId()), entity.getFirstName(),
                entity.getLastName(), new Email(entity.getEmail()), new PasswordHash(entity.getPasswordHash()),
                entity.getRole(), entity.getStatus(), entity.getCreatedAt(), entity.getAuthorizedCameraIds());
    }

    public UserJpaEntity toEntity(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setAccountId(user.getAccountId().userAccountId());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail().value());
        entity.setPasswordHash(user.getPasswordHash().value());
        entity.setRole(user.getRole());
        entity.setStatus(user.getStatus());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setAuthorizedCameraIds(new HashSet<>(user.getAuthorizedCameraIds()));
        return entity;
    }
}
