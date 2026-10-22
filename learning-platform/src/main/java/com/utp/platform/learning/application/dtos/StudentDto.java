package com.utp.platform.learning.application.dtos;

import java.time.Instant;

public record StudentDto(Long id,
                         String recordId,
                         String firstName,
                         String lastName,
                         String email,
                         boolean active,
                         Instant createdAt) {}
