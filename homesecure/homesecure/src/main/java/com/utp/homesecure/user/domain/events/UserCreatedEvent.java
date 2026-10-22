package com.utp.homesecure.user.domain.events;

import com.utp.homesecure.user.domain.enums.Role;
import java.time.Instant;

public record UserCreatedEvent(Long userId, String userAccountId, String email, Role role, Instant occurredOn) {}
