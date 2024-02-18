package mr2.meetingroom02.dojosession.employee.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeResponseDTO {
    private Long id;
    private LocalDate dateOfBirth;
    private String firstName;
    private String gender;
    private String lastName;
    private String middleName;
    private int salary;

}
