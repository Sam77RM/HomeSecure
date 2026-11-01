package com.utp.homesecure.user.application.handlers.commands;

import com.utp.homesecure.user.application.assemblers.UserDtoAssembler;
import com.utp.homesecure.user.application.commands.CreateUserCommand;
import com.utp.homesecure.user.application.commands.SetUserActiveCommand;
import com.utp.homesecure.user.application.dtos.UserDto;
import com.utp.homesecure.user.domain.aggregates.User;
import com.utp.homesecure.user.domain.enums.Role;
import com.utp.homesecure.user.domain.repositories.UserRepository;
import com.utp.homesecure.user.domain.valueobjects.Email;
import com.utp.homesecure.user.domain.valueobjects.PasswordHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCommandHandler {
    private final UserRepository users;
    public UserCommandHandler(UserRepository users) { this.users = users; }

    @Transactional
    public UserDto handle(CreateUserCommand command) {
        Email email = new Email(command.email());
        if (users.existsByEmail(email.value())) {
            throw new IllegalStateException("Ya existe un usuario con ese correo");
        }
        Role role = command.role() == null ? Role.OWNER : command.role();
        User user = new User(command.firstName(), command.lastName(), email,
                new PasswordHash(command.passwordHash()), role);
        return UserDtoAssembler.toDto(users.save(user));
    }

    @Transactional
    public UserDto handle(SetUserActiveCommand command) {
        User user = users.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (command.active()) user.activate(); else user.deactivate();
        return UserDtoAssembler.toDto(users.update(user));
    }
}
