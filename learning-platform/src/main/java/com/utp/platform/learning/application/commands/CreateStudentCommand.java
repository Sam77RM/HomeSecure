package com.utp.platform.learning.application.commands;
public record CreateStudentCommand(
        String firstName,
        String lastName,
        String email) {}
