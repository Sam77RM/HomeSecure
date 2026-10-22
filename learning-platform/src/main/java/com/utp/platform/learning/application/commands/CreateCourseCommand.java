package com.utp.platform.learning.application.commands;
public record CreateCourseCommand(String code, String title, String description, int credits, int capacity) {}
