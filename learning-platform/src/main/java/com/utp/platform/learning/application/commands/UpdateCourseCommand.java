package com.utp.platform.learning.application.commands;

public record UpdateCourseCommand(Long courseId,
                                  String title,
                                  String description,
                                  int credits,
                                  int capacity) {
}
