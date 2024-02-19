package mr2.meetingroom02.dojosession.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mr2.meetingroom02.dojosession.employee.entity.Employee;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeResponseDTO implements Serializable {
    private Long id;
    private LocalDate dateOfBirth;
    private String name;
    private String gender;
    private int salary;

    public static EmployeeResponseDTO fromEntity(Employee employee) {
        return EmployeeResponseDTO.builder()
                .name(employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName())
                .dateOfBirth(employee.getDateOfBirth())
                .gender(employee.getGender())
                .build();
    }

}
