package com.utp.platform.learning.domain.aggregates;

import com.utp.platform.learning.domain.events.StudentCreatedEvent;
import com.utp.platform.learning.domain.valueobjects.AcmeStudentRecordId;
import com.utp.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.Instant;

public class Student extends AbstractDomainAggregateRoot<Student> {

    private Long id;
    private final AcmeStudentRecordId recordId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private boolean active;
    private final Instant createdAt;

    public Student(String firstName, String lastName, String email) {
        this(null, new AcmeStudentRecordId(), firstName, lastName, email, true, Instant.now());
        registerCreatedEvent();
    }

    /**
     * Rehydrates an existing Student. This constructor deliberately does not
     * raise StudentCreatedEvent because loading an aggregate is not creation.
     */
    public Student(Long id, AcmeStudentRecordId recordId, String firstName,
                   String lastName, String email, boolean active, Instant createdAt) {
        if (recordId == null) {
            throw new IllegalArgumentException("Student record id is required");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("Created at is required");
        }
        this.id = id;
        this.recordId = recordId;
        this.firstName = required(firstName, "First name");
        this.lastName = required(lastName, "Last name");
        this.email = validEmail(email);
        this.active = active;
        this.createdAt = createdAt;
    }

    private void registerCreatedEvent() {
        registerDomainEvent(new StudentCreatedEvent(
                id,
                recordId.studentRecordId(),
                email,
                Instant.now()
        ));
    }

    public void deactivate() {
        if (!active) {
            throw new IllegalStateException("Student is already inactive");
        }
        active = false;
    }

    public void activate() {
        if (active) {
            throw new IllegalStateException("Student is already active");
        }
        active = true;
    }

    private static String validEmail(String value) {
        String email = required(value, "Email");
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Email format is invalid");
        }
        return email;
    }

    private static String required(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " is required");
        }
        return value.trim();
    }

    public Long getId() {
        return id;
    }

    public AcmeStudentRecordId getRecordId() {
        return recordId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
