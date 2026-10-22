package com.utp.homesecure.monitoring.infrastructure.integration;

import com.utp.homesecure.monitoring.application.ports.AccountStatusPort;
import com.utp.homesecure.user.domain.repositories.UserRepository;
import com.utp.homesecure.user.domain.enums.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class UserAccountStatusAdapter implements AccountStatusPort {
    private final UserRepository users;
    public UserAccountStatusAdapter(UserRepository users) { this.users = users; }

    @Override public boolean existsAndIsActive(String userAccountId) {
        return users.findByAccountId(userAccountId)
                .map(user -> user.getStatus() == UserStatus.ACTIVE)
                .orElse(false);
    }
}
