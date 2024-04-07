package mr2.meetingroom02.dojosession.lunch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingWeekMealsDTO {

    private String departmentName;

    private String dishName;

    private Long count;

}
