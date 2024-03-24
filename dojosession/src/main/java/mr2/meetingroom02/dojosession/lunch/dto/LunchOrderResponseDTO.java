package mr2.meetingroom02.dojosession.lunch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO;
import mr2.meetingroom02.dojosession.lunch.entity.Meal;
import mr2.meetingroom02.dojosession.lunch.entity.Menu;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class LunchOrderResponseDTO {
    private EmployeeResponseDTO employee;

    private LocalDate scheduleStartDate;

    private LocalDate scheduleEndDate;

    private Map<Menu, Meal> menuMeals;
}
