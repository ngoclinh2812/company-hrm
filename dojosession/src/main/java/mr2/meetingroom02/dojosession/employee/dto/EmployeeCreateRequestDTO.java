package mr2.meetingroom02.dojosession.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import mr2.meetingroom02.dojosession.employee.entity.Gender;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    private int salary;
    private Integer departmentId;
}
