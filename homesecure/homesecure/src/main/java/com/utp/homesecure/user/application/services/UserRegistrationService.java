package com.utp.homesecure.user.application.services;

import com.utp.homesecure.user.domain.aggregates.User;
import com.utp.homesecure.user.domain.enums.Role;
import com.utp.homesecure.user.domain.repositories.UserRepository;
import com.utp.homesecure.user.domain.valueobjects.Email;
import com.utp.homesecure.user.domain.valueobjects.PasswordHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {
    private final UserRepository users;

    public UserRegistrationService(UserRepository users) { this.users = users; }

    /** passwordHash must already be securely hashed by the authentication/security layer. */
    @Transactional
    public User register(String firstName, String lastName, String email, String passwordHash, Role role) {
        Email validatedEmail = new Email(email);
        if (users.existsByEmail(validatedEmail.value())) {
            throw new IllegalStateException("A user with this email already exists");
        }
        User user = new User(firstName, lastName, validatedEmail, new PasswordHash(passwordHash), role);
        return users.save(user);
    }
}
