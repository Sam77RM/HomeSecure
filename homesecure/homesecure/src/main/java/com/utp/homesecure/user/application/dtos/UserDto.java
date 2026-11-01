package com.utp.homesecure.user.application.dtos;

import com.utp.homesecure.user.domain.enums.Role;
import com.utp.homesecure.user.domain.enums.UserStatus;
import java.time.Instant;
import java.util.Set;

public record UserDto(Long id, String accountId, String firstName, String lastName,
                      String email, Role role, UserStatus status, Instant createdAt,
                      Set<Long> authorizedCameraIds) {}
