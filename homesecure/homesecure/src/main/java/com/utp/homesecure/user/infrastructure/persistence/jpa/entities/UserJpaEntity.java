package com.utp.homesecure.user.infrastructure.persistence.jpa.entities;

import com.utp.homesecure.user.domain.enums.Role;
import com.utp.homesecure.user.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_account", columnNames = "user_account_id"),
        @UniqueConstraint(name = "uk_user_email", columnNames = "email")
})
public class UserJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_account_id", nullable = false, updatable = false, length = 36)
    private String accountId;
    @Column(name = "first_name", nullable = false, length = 60)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;
    @Column(name = "email", nullable = false, length = 120)
    private String email;
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private Role role;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private UserStatus status;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_camera_access", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "camera_id", nullable = false)
    private Set<Long> authorizedCameraIds = new HashSet<>();
}
