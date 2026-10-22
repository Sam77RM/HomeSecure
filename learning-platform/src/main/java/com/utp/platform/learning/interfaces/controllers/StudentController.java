package com.utp.platform.learning.interfaces.controllers;

import com.utp.platform.learning.application.dtos.StudentDto;
import com.utp.platform.learning.application.handlers.commands.StudentCommandHandler;
import com.utp.platform.learning.application.handlers.queries.GetStudentQueryHandler;
import com.utp.platform.learning.application.handlers.queries.ListStudentsQueryHandler;
import com.utp.platform.learning.interfaces.mappers.StudentRestMapper;
import com.utp.platform.learning.interfaces.resources.StudentCreateStudentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentCommandHandler command;
    private final GetStudentQueryHandler get;
    private final ListStudentsQueryHandler list;

    public StudentController(StudentCommandHandler command,
                             GetStudentQueryHandler get,
                             ListStudentsQueryHandler list) {
        this.command = command;
        this.get = get;
        this.list = list;
    }

    @PostMapping
    public ResponseEntity<StudentDto> create(StudentCreateStudentRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(command.handle(StudentRestMapper.toCommand(request)));

    }


}

