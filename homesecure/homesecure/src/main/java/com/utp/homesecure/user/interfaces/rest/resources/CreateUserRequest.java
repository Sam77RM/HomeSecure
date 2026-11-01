package com.utp.homesecure.user.interfaces.rest.resources;
import com.utp.homesecure.user.domain.enums.Role;
public record CreateUserRequest(String firstName, String lastName, String email,
                                String passwordHash, Role role) {}
