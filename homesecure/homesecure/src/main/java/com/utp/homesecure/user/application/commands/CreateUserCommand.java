package com.utp.homesecure.user.application.commands;
import com.utp.homesecure.user.domain.enums.Role;
public record CreateUserCommand(String firstName, String lastName, String email,
                                String passwordHash, Role role) {}
