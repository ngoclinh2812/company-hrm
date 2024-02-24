package mr2.meetingroom02.dojosession.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeCreateRequestDTO {

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private int salary;
    private Integer departmentId;
}
