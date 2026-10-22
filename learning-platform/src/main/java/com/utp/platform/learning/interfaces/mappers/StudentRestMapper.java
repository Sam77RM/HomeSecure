package com.utp.platform.learning.interfaces.mappers;

import com.utp.platform.learning.application.commands.CreateStudentCommand;
import com.utp.platform.learning.application.commands.SetStudentActiveCommand;
import com.utp.platform.learning.interfaces.resources.StudentActiveRequest;
import com.utp.platform.learning.interfaces.resources.StudentCreateStudentRequest;

public final class StudentRestMapper {

    public StudentRestMapper() {
    }

    public static CreateStudentCommand toCommand(
            StudentCreateStudentRequest student
    ){
        return new CreateStudentCommand(
                student.firstName(),
                student.lastName(),
                student.email()
        );
    }

    public static SetStudentActiveCommand toCommand(Long id,
                                                    StudentActiveRequest student){
    return new SetStudentActiveCommand(id, student.active());
    }
}
