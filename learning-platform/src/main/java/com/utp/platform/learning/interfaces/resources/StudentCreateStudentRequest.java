package com.utp.platform.learning.interfaces.resources;

public record StudentCreateStudentRequest(
        String firstName,
        String lastName,
        String email
) {
}
