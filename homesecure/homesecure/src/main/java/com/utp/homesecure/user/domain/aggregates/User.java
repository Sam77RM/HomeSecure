package com.utp.homesecure.user.domain.aggregates;

import com.utp.homesecure.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.utp.homesecure.user.domain.enums.Role;
import com.utp.homesecure.user.domain.enums.UserStatus;
import com.utp.homesecure.user.domain.events.UserCreatedEvent;
import com.utp.homesecure.user.domain.valueobjects.Email;
import com.utp.homesecure.user.domain.valueobjects.PasswordHash;
import com.utp.homesecure.user.domain.valueobjects.UserAccountId;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class User extends AbstractDomainAggregateRoot<User> {
    private Long id;
    private final UserAccountId accountId;
    private String firstName;
    private String lastName;
    private Email email;
    private PasswordHash passwordHash;
    private Role role;
    private UserStatus status;
    private final Instant createdAt;
    private final Set<Long> authorizedCameraIds;

    public User(String firstName, String lastName, Email email, PasswordHash passwordHash, Role role) {
        this(null, new UserAccountId(), firstName, lastName, email, passwordHash, role,
                UserStatus.ACTIVE, Instant.now(), Set.of());
        registerDomainEvent(new UserCreatedEvent(id, accountId.userAccountId(), this.email.value(), role, Instant.now()));
    }

    public User(Long id, UserAccountId accountId, String firstName, String lastName, Email email,
                PasswordHash passwordHash, Role role, UserStatus status, Instant createdAt,
                Set<Long> authorizedCameraIds) {
        if (accountId == null) throw new IllegalArgumentException("User account id is required");
        if (role == null) throw new IllegalArgumentException("Role is required");
        if (status == null) throw new IllegalArgumentException("User status is required");
        if (createdAt == null) throw new IllegalArgumentException("Created at is required");
        this.id = id;
        this.accountId = accountId;
        this.firstName = required(firstName, "First name");
        this.lastName = required(lastName, "Last name");
        this.email = requireEmail(email);
        this.passwordHash = requirePassword(passwordHash);
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.authorizedCameraIds = new HashSet<>(authorizedCameraIds == null ? Set.of() : authorizedCameraIds);
    }

    public void updateProfile(String firstName, String lastName, Email email) {
        this.firstName = required(firstName, "First name");
        this.lastName = required(lastName, "Last name");
        this.email = requireEmail(email);
    }

    public void changePasswordHash(PasswordHash passwordHash) { this.passwordHash = requirePassword(passwordHash); }

    public void changeRole(Role newRole) {
        if (newRole == null) throw new IllegalArgumentException("Role is required");
        if (newRole == role) throw new IllegalStateException("User already has this role");
        role = newRole;
        if (role != Role.GUEST) authorizedCameraIds.clear();
    }

    public void deactivate() {
        if (status == UserStatus.INACTIVE) throw new IllegalStateException("User is already inactive");
        status = UserStatus.INACTIVE;
    }

    public void activate() {
        if (status == UserStatus.ACTIVE) throw new IllegalStateException("User is already active");
        status = UserStatus.ACTIVE;
    }

    public void grantCameraAccess(Long cameraId) {
        if (cameraId == null) throw new IllegalArgumentException("Camera id is required");
        if (role != Role.GUEST) throw new IllegalStateException("Only guests need explicit camera access");
        authorizedCameraIds.add(cameraId);
    }

    public void revokeCameraAccess(Long cameraId) { authorizedCameraIds.remove(cameraId); }

    public boolean canViewCamera(Long cameraId) {
        if (status != UserStatus.ACTIVE) return false;
        return role != Role.GUEST || authorizedCameraIds.contains(cameraId);
    }

    public boolean canControlAlarm() { return isActive() && (role == Role.ADMINISTRATOR || role == Role.OWNER); }
    public boolean canManageDevices() { return isActive() && role == Role.ADMINISTRATOR; }
    public boolean canManageUsers() { return isActive() && role == Role.ADMINISTRATOR; }

    private static String required(String value, String name) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(name + " is required");
        return value.trim();
    }
    private static Email requireEmail(Email value) {
        if (value == null) throw new IllegalArgumentException("Email is required");
        return value;
    }
    private static PasswordHash requirePassword(PasswordHash value) {
        if (value == null) throw new IllegalArgumentException("Password hash is required");
        return value;
    }

    public Long getId() { return id; }
    public UserAccountId getAccountId() { return accountId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Email getEmail() { return email; }
    public PasswordHash getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
    public UserStatus getStatus() { return status; }
    public boolean isActive() { return status == UserStatus.ACTIVE; }
    public Instant getCreatedAt() { return createdAt; }
    public Set<Long> getAuthorizedCameraIds() { return Collections.unmodifiableSet(authorizedCameraIds); }
}
